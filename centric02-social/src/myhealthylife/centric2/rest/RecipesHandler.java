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
	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public RecipeList getRecipesByCalories(@QueryParam("maxCal") Integer maxCal){
		RecipeList rl=new RecipeList();
		rl.setRecipes(Recipe.getAll());
		Iterator<Recipe> it=rl.getRecipes().iterator();
		
		while(it.hasNext()){
			Recipe r=it.next();
			r.computeFoods();
			r.computeCalories();
			
			
		}
		
		for(int i=0;i<rl.getRecipes().size();i++){
			Recipe r=rl.getRecipes().get(i);
			if(maxCal!=null){
				if(r.getCalories()>maxCal){
					rl.getRecipes().remove(r);
					i--;
				}
			}
		}
		
		return rl;
	}
	
	@GET
	@Path("/id/{recipeID}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getRecipeById(@PathParam("recipeID") Long recipeID){
		Recipe r=Recipe.getRecipeById(recipeID);
		if(r==null)
			return Utilities.throwResourceNotFound();
		
		r.computeFoods();
		r.computeCalories();
		
		return Utilities.throwOK(r);
	}
	
	@GET
	@Path("/name/{recipeName}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public RecipeList searchRecipesByName(@PathParam("recipeName") String recipeName){
		RecipeList rl=new RecipeList();
		
		rl.setRecipes(Recipe.getRecipesByName(recipeName));
		
		for (int i=0;i<rl.getRecipes().size();i++){
			rl.getRecipes().get(i).computeFoods();
			rl.getRecipes().get(i).computeCalories();
		}
		
		return rl;
	}
	
	
	@POST
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Recipe saveRecipe(Recipe r){
		r.computeIDS();
		r=Recipe.saveRecipe(r);
		r.computeFoods();
		r.computeCalories();
		return r;
	}
	
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
	    
	    
	    List<Boolean> measureTypesInserted = new ArrayList<>();
	    List<Integer> measureTypesInsertedCount = new ArrayList<>();
	    
	    ArrayList<ArrayList<Double>> lastMeasures = new ArrayList<ArrayList<Double>>();
	    
	    for(int i=0;i<measureTypes.size();i++) {
	    	
	    	measureTypesInserted.add(false);
	    	measureTypesInsertedCount.add(0);
	    	lastMeasures.add(new ArrayList<>());
	    	
	    }
	    
	    // Retrieve last 3 measures
	    for(int i=0;i<measureHistory.size();i++) {
	    	System.out.println("Cycle: " + i);
	    	Measure extractedMeasure = measureHistory.get(i);
	    	
	    	for(int j=0;j<measureTypes.size();j++) {
	
	        	System.out.println("Cycle [J]: " + j);
	    		String extractedType = measureTypes.get(j);
	    		
	    		if(extractedMeasure.getMeasureType().equals(extractedType)) {
	    			
	    			if(!measureTypesInserted.get(j)) {
	        			
	    				Integer typeCount = measureTypesInsertedCount.get(j);
	        			typeCount++;
	        			measureTypesInsertedCount.set(j, typeCount);
	        			
	        			lastMeasures.get(j).add(extractedMeasure.getMeasureValue());
	        			
	        			this.checkMeasuresCount(measureTypesInserted, measureTypesInsertedCount);
	        			
	    			}
	    		}
	    		
	    	}
	    	
	    }
		
	    
	    List<String> preferredType = this.getPreferredSentenceType(lastMeasures, measureTypes);
	    System.out.println("Preferred: " + preferredType.get(0) + " " + preferredType.get(1));
	    String preferredTypeName = preferredType.get(0);
	    Boolean preferredTypeTrend = Boolean.valueOf(preferredType.get(1));
	    
	    // Only applies to steps measure
	    if(preferredTypeTrend && preferredTypeName.equals("steps")) {
	    	this.MAX_CAL_PER_RECIPE = Integer.MAX_VALUE;
	    }
	    System.out.println("Calories param: " + MAX_CAL_PER_RECIPE);
	    // Gets the recipes to return
	    RecipeList recipeListToReturn = this.getRecipesByCalories(this.MAX_CAL_PER_RECIPE);
	    
	    
	    // Returns the recipes
	    return Utilities.throwOK(recipeListToReturn);
			
	}
		
		
		
	private void checkMeasuresCount(List<Boolean> measureTypesInserted, List<Integer> measureTypesInsertedCount) {
			
			for(int i=0;i<measureTypesInsertedCount.size();i++) {
				
				if(measureTypesInsertedCount.get(i)>4) {
					
					Boolean enough = measureTypesInserted.get(i);
					enough = true;
					measureTypesInserted.set(i, enough);
					
				}
				
			}
			
		}
		
		
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
