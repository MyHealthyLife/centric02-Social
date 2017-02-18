package myhealthylife.centric2.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import myhealthylife.centric2.rest.model.Rank;
import myhealthylife.centric2.util.ServicesLocator;
import myhealthylife.centric2.util.Utilities;
import myhealthylife.dataservice.soap.DataService;
import myhealthylife.dataservice.soap.HealthProfile;
import myhealthylife.dataservice.soap.Measure;
import myhealthylife.dataservice.soap.MeasureHistory;
import myhealthylife.dataservice.soap.Person;
import myhealtylife.optimalparamters.soap.AgeRange;
import myhealtylife.optimalparamters.soap.OptimalParameters;
import myhealtylife.optimalparamters.soap.Parameter;
import myhealtylife.optimalparamters.soap.ParametersList;


@Path("/ranking")
public class RankingHandler {

	final private double STEPS_POINTS = 0.01;
	final private double WEIGHT_DISTANCE_POINTS_MAX = 100;
	final private double WEIGHT_DISTANCE_POINTS_MIN = 1;
	final private double WEIGHT_DISTANCE_MULFACTOR = 5;
	
	@Path("/{username}")
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getUserRank(@PathParam("username") String username){

		int yearToday = Calendar.getInstance().get(Calendar.YEAR);
		
		DataService ds = ServicesLocator.getDataServiceConnection();
		OptimalParameters gs = ServicesLocator.getOptimalParameterConnection();
		
		// Gets the global optimal parameters
		List<Parameter> pl = gs.readOptimalParameters().getParameters();
		
		// Gets the name of all the users
		List<Person> personList = ds.listPeople().getPerson();
		
		// The set with the assigned points for each user
		Map<String,Double> usersAndPoints = new HashMap<String,Double>();
		
		
		// For all the people in the database
		for(int i=0;i<personList.size();i++) {
			
			// Gets the data of the user
			Person singlePerson = personList.get(i);
			HealthProfile spHp = singlePerson.getHealthProfile();
			MeasureHistory mh = ds.getMeasureHistory(singlePerson.getIdPerson());
			
			if(mh!=null) {
			
				//List<Measure> measureSP = singlePerson.getHealthProfile().getCurrentHealth().getMeasure();
				List<Measure> measureSP = mh.getMeasures();
				
				// Assigns point to each user for the number of steps and weight
				for(int j=0;j<measureSP.size();j++) {

					Measure singleMeasure = measureSP.get(j);
				
					// Assigns points for "steps" measure
					if(singleMeasure.getMeasureType().equals("steps")) {
						
						// Check if we do not have a record for that user
						if(usersAndPoints.get(singlePerson.getUsername())==null) {
							usersAndPoints.put(singlePerson.getUsername(), singleMeasure.getMeasureValue() * this.STEPS_POINTS);
						}
						
						// Otherwise we just have to update the existing one
						else {
							Double previousValue = usersAndPoints.get(singlePerson.getUsername());
							usersAndPoints.put(singlePerson.getUsername(), previousValue += (singleMeasure.getMeasureValue() * this.STEPS_POINTS));
						}
						
					}

				}

			}
			
			
			// Checks if the healthprofile is null
			if(spHp!=null) {
				
				List<Measure> measureSP = singlePerson.getHealthProfile().getCurrentHealth().getMeasure();
				
				// Assigns point to each user for the number of steps and weight
				for(int j=0;j<measureSP.size();j++) {
					
					Measure singleMeasure = measureSP.get(j);
					
					// Assigns points for "weight" measure
					if(singleMeasure.getMeasureType().equals("weight")) {
	
						// Calculates the age of the person
						int personAge = yearToday - singlePerson.getBirthdate().getYear();
						
						
						for(int k=0;k<pl.size();k++) {
							
							Parameter singleParameter = pl.get(k);
							AgeRange spAgeRange = singleParameter.getAgeRange();
							
							// Checks if the person is in the considered age range and other parameters
							if(spAgeRange.getFromAge() < personAge && personAge < spAgeRange.getToAge() && singleParameter.getSex().equals(singlePerson.getSex()) && singleParameter.getParameterName().equals("weight")) {
								
								// Assigns points for the weight
								Double pointsToAssign = this.WEIGHT_DISTANCE_POINTS_MAX - (Math.abs(singleParameter.getValue() - singleMeasure.getMeasureValue()) * this.WEIGHT_DISTANCE_MULFACTOR);
								
								// The minimum points to assign is one
								if(pointsToAssign < 0) {
									pointsToAssign = this.WEIGHT_DISTANCE_POINTS_MIN;
								}
								
								// Add or update the points
								if(usersAndPoints.get(singlePerson.getUsername())==null) {
									usersAndPoints.put(singlePerson.getUsername(), pointsToAssign);
								}
								
								else {
									Double previousValue = usersAndPoints.get(singlePerson.getUsername());
									usersAndPoints.put(singlePerson.getUsername(), previousValue += (pointsToAssign));
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		// Sorts the hashmap by its values
		Map<String, Double> usersAndPointsSorted = this.sortByValue(usersAndPoints);
		
		// Builds a Rank object and calculates a filtered ranking based on the position of the user
		Rank rankObj = new Rank();
		rankObj.setCompactRanking(rankObj.getFinalRankingFiltered(username, usersAndPointsSorted));
		
        // Returns the filtered ranking
		return Utilities.throwOK(rankObj);
		
	}
	
	
	
	
	/**
	 * Sorts a map object by its values
	 * @param unsortMap The map which has a random order
	 * @return An ordered Map<String, Double> by its values 
	 */
	private Map<String, Double> sortByValue(Map<String, Double> unsortMap) {

        // Convert Map to List of Map
        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // Sort list with Collections.sort(), provide a custom Comparator
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        
        // Reverse the list so we have the DESC order
        Collections.reverse(list);

        // Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
        }
    }
	
	
}
