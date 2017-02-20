package myhealthylife.centric2.rest;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import myhealthylife.centric2.rest.model.DedicatedSentence;
import myhealthylife.centric2.util.ServicesLocator;
import myhealthylife.centric2.util.Utilities;
import myhealthylife.dataservice.soap.DataService;
import myhealthylife.dataservice.soap.Person;
import myhealthylife.nutritionservice.soap.FoodList;
import myhealthylife.nutritionservice.soap.Foods;
import myhealthylife.sentencegenerator.soap.Sentence;
import myhealthylife.sentencegenerator.soap.Sentences;



@Path("/sentence")
public class SentenceHandler {

	
	@Path("/{username1}/{username2}/{sentenceId}")
	@POST
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response dedicateSentenceToUser(@PathParam("username1") String username1, @PathParam("username2") String username2, @PathParam("sentenceId") Integer sentenceId){

		DataService ds = ServicesLocator.getDataServiceConnection();
		Sentences ss = ServicesLocator.getSentenceGeneratorConnection();
		
		// Gets the first and the second user
		Person user1 = ds.getPersonByUsername(username1);
		Person user2 = ds.getPersonByUsername(username2);
		
		// Gets the sentence the user wants to dedicate
		Sentence sentenceToDedicate = ss.readSentence(sentenceId);
		
		// Checks if all three entities exist
		if(user1==null && user2==null && sentenceToDedicate==null) {
			
			return Utilities.throwResourceNotFound();
			
		}
		
		DedicatedSentence dedicatedS = new DedicatedSentence();

		// Fills the entity with some data
		dedicatedS.setIdUserOne(user1.getIdPerson());
		dedicatedS.setIdUserTwo(user2.getIdPerson());
		dedicatedS.setUsernameOne(user1.getUsername());
		dedicatedS.setUsernameTwo(user2.getUsername());
		dedicatedS.setIdSentence(sentenceToDedicate.getIdSentence());
		dedicatedS.setSentenceText(sentenceToDedicate.getText());
		dedicatedS.setInsertionTime(new Date(System.currentTimeMillis()));
		
		// Saves the entity into the database
		DedicatedSentence.saveDedicatedSentence(dedicatedS);
        
        // Returns the dedicated sentence
		return Utilities.throwOK(dedicatedS);
		
	}
	
	
	
	@Path("/{username1}/{username2}/{type}/{motive}")
	@POST
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response dedicateSentenceToUserByType(@PathParam("username1") String username1, @PathParam("username2") String username2, @PathParam("type") String sentenceType, @PathParam("motive") String motive){

		DataService ds = ServicesLocator.getDataServiceConnection();
		Sentences ss = ServicesLocator.getSentenceGeneratorConnection();
		
		// Gets the first and the second user
		Person user1 = ds.getPersonByUsername(username1);
		Person user2 = ds.getPersonByUsername(username2);
		
		Boolean motiveToSet = false;
		
		// Sets the motive parameter
		if(motive.equals("gain")) {
			motiveToSet = true;
		}
		else if(motive.equals("loss")) {
			motiveToSet = false;
		}
		else {

			return Utilities.throwNoContent();
			
		}
		
		// Gets the sentences to dedicate
		Sentence sentenceToDedicate = ss.readRandomSentenceByTypeAndTrend(sentenceType, motiveToSet);
		
		// Checks if all three entities exist
		if(user1==null && user2==null && sentenceToDedicate==null) {
			
			return Utilities.throwResourceNotFound();
			
		}
		
		DedicatedSentence dedicatedS = new DedicatedSentence();

		// Fills the entity with some data
		dedicatedS.setIdUserOne(user1.getIdPerson());
		dedicatedS.setIdUserTwo(user2.getIdPerson());
		dedicatedS.setUsernameOne(user1.getUsername());
		dedicatedS.setUsernameTwo(user2.getUsername());
		dedicatedS.setIdSentence(sentenceToDedicate.getIdSentence());
		dedicatedS.setSentenceText(sentenceToDedicate.getText());
		dedicatedS.setInsertionTime(new Date(System.currentTimeMillis()));
		
		// Saves the entity into the database
		DedicatedSentence.saveDedicatedSentence(dedicatedS);
        
        // Returns the dedicated sentence
		return Utilities.throwOK(dedicatedS);
		
	}
	
	
	
	@Path("/{username}")
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getDedicatedSentences(@PathParam("username") String username){

		DataService ds = ServicesLocator.getDataServiceConnection();
		Sentences ss = ServicesLocator.getSentenceGeneratorConnection();
		
		// Gets the user
		Person user = ds.getPersonByUsername(username);
		
		// Checks if all three entities exist
		if(user==null) {
			
			return Utilities.throwResourceNotFound();
			
		}
		
		// Lists of dedicated sentences
		List<DedicatedSentence> dedicatedSListAll = DedicatedSentence.getAll();
		List<DedicatedSentence> dedicatedSListForUser = new ArrayList<>();
		
		for(int i=0;i<dedicatedSListAll.size();i++) {
			
			DedicatedSentence currentDS = dedicatedSListAll.get(i);
			
			// Checks if the second user id is equal to the user
			if(currentDS.getIdUserTwo()==user.getIdPerson()) {
				
				if(currentDS.getInsertionTime()!=null) {

					Calendar calDS = Calendar.getInstance();
					calDS.setTime(currentDS.getInsertionTime());
					
					Calendar calWeekEarly = Calendar.getInstance();
					calWeekEarly.add(Calendar.DATE, -7);

					if(calDS.getTime().getTime() > calWeekEarly.getTime().getTime()) {
					
						dedicatedSListForUser.add(currentDS);
					
					}
				}
				
			}
		}
		
		// Checks if the list is empty
		if(dedicatedSListForUser.isEmpty()) {
			
			return Utilities.throwNoContent();
			
		}
        
        // Returns the list of dedicated sentence for the user
		return Utilities.throwOK(dedicatedSListForUser);
		
	}
	
	
}
