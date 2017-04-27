package myhealthylife.centric2.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import jersey.repackaged.com.google.common.collect.Lists;
import myhealthylife.centric2.rest.model.Recipe;
import myhealthylife.centric2.rest.model.RecipeList;
import myhealthylife.centric2.util.ServicesLocator;
import myhealthylife.centric2.util.Utilities;
import myhealthylife.dataservice.soap.DataService;
import myhealthylife.dataservice.soap.Measure;
import myhealthylife.dataservice.soap.Person;
import myhealthylife.nutritionservice.soap.Foods;

@Path("/recipe")
public class RecipesHandler {
	
	/**
	 * Retrieves all the recipes in the database of service04 that have a maximum amount of calories specified by the user in the input parameters
	 * @param maxCal max calories for the recipes
	 * @return
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public RecipeList getRecipesByCalories(@QueryParam("maxCal") Integer maxCal){
		RecipeList rl=new RecipeList();
		rl.setRecipes(Recipe.getAll());//get all recipes
		Iterator<Recipe> it=rl.getRecipes().iterator();
		
		/*in this db are stored only the id of the foods
		 * for every recipe get the food dtalis from the service4 and compute its calories*/
		while(it.hasNext()){
			Recipe r=it.next();
			r.computeFoods(); //retrieve all food details for the recipe
			r.computeCalories();//compute calories for the recipe
			
			
		}
		
		for(int i=0;i<rl.getRecipes().size();i++){
			Recipe r=rl.getRecipes().get(i);
			if(maxCal!=null){ //if the attribute max calories is set remove every recipe which exceed the max calories
				if(r.getCalories()>maxCal){
					rl.getRecipes().remove(r);
					i--;
				}
			}
		}
		
		return rl;
	}
	
	/**
	 * Retrieves a recipe memorized in this service. The retrieved recipe is the one identified by 'recipeId' parameter.
	 * @param recipeID
	 * @return
	 */
	@GET
	@Path("/id/{recipeID}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getRecipeById(@PathParam("recipeID") Long recipeID){
		Recipe r=Recipe.getRecipeById(recipeID);
		
		/*check if recipe exits*/
		if(r==null)
			return Utilities.throwResourceNotFound();
		
		r.computeFoods(); //retrieve all food details for the recipe
		r.computeCalories();//compute calories for the recipe
		
		return Utilities.throwOK(r);
	}
	
	/**
	 * Retrieves a Recipe memorized in this service. It performs a search in the local database in order to retrieve all the recipes with a similar name.
	 * @param recipeName
	 * @return
	 */
	@GET
	@Path("/name/{recipeName}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public RecipeList searchRecipesByName(@PathParam("recipeName") String recipeName){
		RecipeList rl=new RecipeList();
		
		rl.setRecipes(Recipe.getRecipesByName(recipeName));//get all recipe by name
		
		/*for every recipe compute calories and retrieve food details*/
		for (int i=0;i<rl.getRecipes().size();i++){ 
			rl.getRecipes().get(i).computeFoods();
			rl.getRecipes().get(i).computeCalories();
		}
		
		return rl;
	}
	
	/**
	 * Saves a recipe the user placed in the body of the request. The recipe will be saved in the local database. 
	 * Once the recipe is saved it will be also returned to the caller.
	 * @param r
	 * @return
	 */
	@POST
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Recipe saveRecipe(Recipe r){
		r.computeIDS(); //retrieve ids from foods before saving
		r=Recipe.saveRecipe(r); //save recipe
		r.computeFoods();
		r.computeCalories();
		return r;
	}
	
	/**
	 * Updates an existing recipe already present in the database. The recipe with the updated values is provided by the user
	 * and needs to be placed in the body of the request. The recipe will be updated in the local database. 
	 * Once the recipe is saved it will be also returned to the caller.
	 * @param r
	 * @return
	 */
	@PUT
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response updateRecipe(Recipe r){
		Recipe stored=Recipe.getRecipeById(r.getRecipeId());
		
		if(stored==null)
			return Utilities.throwResourceNotFound();
		
		r.computeIDS();
		
		stored.setDescription(r.getDescription());
		stored.setIngredientsIDs(r.getIngredientsIDs());
		stored.setName(r.getName());
		
		stored=Recipe.updateRecipe(stored);
		stored.computeFoods();
		stored.computeCalories();
		
		return Utilities.throwOK(stored);
		
	}
	
	/**
	 *  Delete an existing recipe already present in the database. 
	 *  The user needs to specify the identifier of the recipe in the path parameters. 
	 *  Once the recipe is saved it will be also returned to the caller.
	 * @param recipeId
	 * @return
	 */
	@DELETE
	@Path("/{recipeId}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response deleteRecipe(@PathParam("recipeId") String recipeId){
		
		long id;
		try{
			id=Long.parseLong(recipeId,10);
		}
		catch (Exception e) {
			return Utilities.throwBadRequest();
		}
		
		if(Recipe.getRecipeById(id)==null)
			return Utilities.throwResourceNotFound();
		
		Recipe.removeRecipe(id);
		return Utilities.throwOK(new Long(recipeId));
	}
	
	
	
	private Integer MAX_CAL_PER_RECIPE = 1000;
	
	/**
	 * Gets suggested recipes for the user. The suggested recipes are retrieved by analysing the measures of the user. If the user,
	 * for instance, needs to loose weight the system will set
	 * a maximum amount of calories for a recipe and will perform a search using that parameter to filter the results.
	 * @param username
	 * @return
	 */
	@Path("/suggested/{username}")
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getSuggestedRecipes(@PathParam("username") String username){
	
	
		Foods fs = ServicesLocator.getFoodServiceConnection();
		DataService ds = ServicesLocator.getDataServiceConnection();
		
		// Gets the person related to that username
	    Person person = ds.getPersonByUsername(username);
	    
	    // If the username does not exist it throws an error
	    if(person==null) {
			return Utilities.throwResourceNotFound();
	    }
	    
	    // Gets the current measure and the last 3 available measures
	    List<Measure> currentMeasures = person.getHealthProfile().getCurrentHealth().getMeasures();
	    
	    // Gets all the measures for that user and all the types available
	    List<Measure> measureHistory = ds.getMeasureHistory(person.getIdPerson()).getMeasures();
	    List<String> measureTypes = ds.getMeasureTypes().getMeasureTypes();
	    
	    // Lists used to check the count of the measures for each type and if it has been inserted or not
	    List<Boolean> measureTypesInserted = new ArrayList<>();
	    List<Integer> measureTypesInsertedCount = new ArrayList<>();
	    
	    // List of the most recent measures
	    ArrayList<ArrayList<Double>> lastMeasures = new ArrayList<ArrayList<Double>>();
	    
	    // Init of the boolean list and the count of the measures for each type
	    for(int i=0;i<measureTypes.size();i++) {
	    	
	    	measureTypesInserted.add(false);
	    	measureTypesInsertedCount.add(0);
	    	lastMeasures.add(new ArrayList<>());
	    	
	    }
	    
	    // Retrieve last 5 measures
	    for(int i=0;i<measureHistory.size();i++) {
	    	
	    	// Gets the measure history of the user
	    	Measure extractedMeasure = measureHistory.get(i);
	    	
	    	// For each measure type we get all the most recent measures
	    	for(int j=0;j<measureTypes.size();j++) {
	
	    		String extractedType = measureTypes.get(j);
	    		
	    		// Check if the extracted type is equal to the one of the current selected measure
	    		if(extractedMeasure.getMeasureType().equals(extractedType)) {
	    			
	    			if(!measureTypesInserted.get(j)) {
	        			
	    				// Update the type count
	    				Integer typeCount = measureTypesInsertedCount.get(j);
	        			typeCount++;
	        			measureTypesInsertedCount.set(j, typeCount);
	        			
	        			// Adds the measure in the list
	        			lastMeasures.get(j).add(extractedMeasure.getMeasureValue());
	        			
	        			// Updates eventually the boolean list (only if the measures for that type has reached count 5
	        			this.checkMeasuresCount(measureTypesInserted, measureTypesInsertedCount);
	        			
	    			}
	    		}
	    		
	    	}
	    	
	    }
		
	    
	    // Gets the preferred type (the one with the highest slope)
	    List<String> preferredType = this.getPreferredSentenceType(lastMeasures, measureTypes);
	    System.out.println("Preferred: " + preferredType.get(0) + " " + preferredType.get(1));
	    String preferredTypeName = preferredType.get(0);
	    Boolean preferredTypeTrend = Boolean.valueOf(preferredType.get(1));
	    
	    // Only applies to steps measure
	    if(preferredTypeTrend && preferredTypeName.equals("steps")) {
	    	this.MAX_CAL_PER_RECIPE = Integer.MAX_VALUE;
	    }
	    
	    // Gets the recipes to return
	    RecipeList recipeListToReturn = this.getRecipesByCalories(this.MAX_CAL_PER_RECIPE);
	    
	    
	    // Returns the recipes
	    return Utilities.throwOK(recipeListToReturn);
			
	}
		
		
	/**
	 * Check if the measure count is 5 or more (we are interested in the trend of the last 5 measures
	 * @param measureTypesInserted The list of bools that specify if a type has been inserted or not
	 * @param measureTypesInsertedCount The measures count for each measure type
	 */
	private void checkMeasuresCount(List<Boolean> measureTypesInserted, List<Integer> measureTypesInsertedCount) {
		
		for(int i=0;i<measureTypesInsertedCount.size();i++) {
			
			if(measureTypesInsertedCount.get(i)>4) {
				
				Boolean enough = measureTypesInserted.get(i);
				enough = true;
				measureTypesInserted.set(i, enough);
				
			}
			
		}
		
	}
		
	
	/**
	 * Gets the preferred measure type that the system will use to retrieve a sentence (the one with the highest slope)
	 * @param lastMeasures The object describing the last measures for each measure type
	 * @param measureTypes The list of measures present in the system
	 * @return The list of preferred measure type (the one with the highest slope)
	 */
	private List<String> getPreferredSentenceType(ArrayList<ArrayList<Double>> lastMeasures, List<String> measureTypes) {
			
		
		List<Double> slopes = new ArrayList<>();
		
		for(int i=0;i<lastMeasures.size();i++) {

			List<Double> dataList = Lists.reverse(lastMeasures.get(i));
			
			// Creating regression object, passing true to have intercept term
	        SimpleRegression simpleRegression = new SimpleRegression(true);
	
	        // Passing data to the model
	        for(int j=0;j<dataList.size();j++) {
	        	System.out.println("Added: " + dataList.get(j));
	        	simpleRegression.addData(j,dataList.get(j));
	        }
	
	        
	        // Querying for model parameters
	        System.out.println("slope = " + simpleRegression.getSlope());
	        Double singleSlope = simpleRegression.getSlope();
	        
	    	
	    	slopes.add(singleSlope);
	        
	        //System.out.println("intercept = " + simpleRegression.getIntercept());
	
	        // Trying to run model for unknown data
	        //System.out.println("prediction for 1.5 = " + simpleRegression.predict(1.5));
			
		}
		
		List<String> returnValues = new ArrayList<>();
		
	    int index = this.findIndexOfMaxValue(slopes);
	    System.out.println("Index: " + measureTypes.get(index));
	    
	    returnValues.add(measureTypes.get(index));
	    
	    if(slopes.get(index)<0) {
	    	returnValues.add("false");
	    }
	    else {
	    	returnValues.add("true");
	    }
	    
		return returnValues;
	}
	
	
	public int findIndexOfMaxValue(List<Double> slopes) {
	    
	
	    int maxIndex = 0;
	    for (int i=1;i<slopes.size()-1;i++) {
	        double newnumber = Math.abs(slopes.get(i));
	        if ((newnumber > Math.abs(slopes.get(maxIndex)))) {
	            maxIndex = i;
	        }
	    }
	    
	    System.out.println(maxIndex);
	
	    return maxIndex;
	}


}
