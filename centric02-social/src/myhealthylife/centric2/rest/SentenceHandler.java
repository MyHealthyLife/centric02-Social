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
import javax.xml.ws.Holder;

import myhealthylife.centric2.rest.model.DedicatedSentence;
import myhealthylife.centric2.util.ServicesLocator;
import myhealthylife.centric2.util.Utilities;
import myhealthylife.dataservice.soap.DataService;
import myhealthylife.dataservice.soap.Person;
import myhealthylife.nutritionservice.soap.Food;
import myhealthylife.nutritionservice.soap.Foods;
import myhealthylife.sentencegenerator.soap.Sentence;
import myhealthylife.sentencegenerator.soap.Sentences;



@Path("/sentence")
public class SentenceHandler {

	/**
	 * Sends a specific sentence to a specific user
	 * @param username1 The sender user
	 * @param username2 The destination user
	 * @param sentenceId The sentence identifier of the sentence in service 02
	 * @return The dedicated sentence object that has been sent to the other user
	 */
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
	
	
	/**
	 * Sends a sentence to a specific user. The selected sentence is chosen by the system, which considers the
	 * type (e.g. steps) and the motive (e.g. gain) specified by the sender as input parameters
	 * @param username1 The sender user
	 * @param username2 The destination user
	 * @param sentenceType The sentence type that must be considered by the system while choosing the sentence
	 * @param motive The sentence motive (either gain or loss) that must be considered by the system while choosing the sentence
	 * @return The dedicated sentence object that has been sent to the other user
	 */
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
	
	
	/**
	 * Gets all the sentences the other users dedicated to you
	 * @param username The user that wants to retrieve all the sentences the other users dedicated to him/her
	 * @return The list of dedicated sentence the other users sent to this user
	 */
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
	
	
	/**
	 * Gets a specific sentence based on the identifier in input. The identifier is the one related to service 02
	 * @param username The user that wants to retrieve the sentence
	 * @param sentenceId The identifier of the sentence that needs to be retrieved
	 * @return The Sentence object containing the information related to that sentence
	 */
	@Path("/{username}/{sentenceId}")
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getSpecificSentences(@PathParam("username") String username, @PathParam("sentenceId") Integer sentenceId){

		DataService ds = ServicesLocator.getDataServiceConnection();
		Sentences ss = ServicesLocator.getSentenceGeneratorConnection();
		
		// Gets the user
		Person user = ds.getPersonByUsername(username);
		
		// Checks if the user exists
		if(user==null) {
			
			return Utilities.throwResourceNotFound();
			
		}
		
		// Get specific sentence based on the identifier provided by the user
		Sentence sentenceToReturn = ss.readSentence(sentenceId);
		
		// Checks if the sentence is null
		if(sentenceToReturn==null) {
			
			return Utilities.throwNoContent();
			
		}
        
        // Returns the sentence to the user
		return Utilities.throwOK(sentenceToReturn);
		
	}
	
	
	
	
	/**
	 * Gives the possibility to create a sentence
	 * @param sentenceToSave The Sentence object containing the details of the Sentence
	 * @return The new Sentence that has been created
	 */
	@Path("/")
	@POST
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createSentence(Sentence sentenceToSave){
		
		Sentences ss = ServicesLocator.getSentenceGeneratorConnection();
		
		// The new sentence that needs to be created
		Sentence newSentence = new Sentence();
		
		// Tries to save the food passed in input
        if(sentenceToSave.getText()!=null) {
        	
        	Holder<Sentence> sentenceHolder = new Holder<Sentence>(sentenceToSave);
            ss.createSentence(sentenceHolder);
            newSentence = sentenceHolder.value;
        	
        }
		
		// Checks the result
        if(newSentence!=null) {

    		return Utilities.throwOK(newSentence);
    		
        }
        
        // Otherwise it returns an error
        return Utilities.throwConflict();
		
	}
	
	
}
