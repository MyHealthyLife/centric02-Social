package myhealthylife.centric2.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import myhealthylife.centric2.util.ServicesLocator;
import myhealthylife.centric2.util.Utilities;
import myhealthylife.nutritionservice.soap.Food;
import myhealthylife.nutritionservice.soap.FoodList;
import myhealthylife.nutritionservice.soap.FoodType;
import myhealthylife.nutritionservice.soap.FoodTypeList;
import myhealthylife.nutritionservice.soap.Foods;

@Path("/food")
public class FoodHandler {

	@Path("/{type}")
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getFoodsByTypeAndCalories(@PathParam("type") String typeName, @QueryParam("maxCal") Integer maxCal){

		Foods fs = ServicesLocator.getFoodServiceConnection();
		
		// Gets the foods to return
        FoodList foodListToReturn = fs.findFoodByTypeFiltered(typeName, maxCal);
        
        // Returns the list of foods
		return Utilities.throwOK(foodListToReturn);
		
	}
	
	
	@Path("/all")
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getAllFoods(){

		Foods fs = ServicesLocator.getFoodServiceConnection();
		
		// Gets the foods to return (all the foods present in the database)
        FoodList foodListToReturn = fs.readFoodList();
        
        // Returns the list of foods
		return Utilities.throwOK(foodListToReturn);
		
	}
	
	
	@Path("/foodTypes")
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getAllFoodTypes(){

		Foods fs = ServicesLocator.getFoodServiceConnection();
		
		// Gets the food types to return (all the types present in the database)
        FoodTypeList foodTypesListToReturn = fs.readFoodTypeList();
        List<FoodType> foodTypesList = foodTypesListToReturn.getFoodType();
        
        // Map object is required to filter results
        Map<String,Long> listWithoutDuplicates = new HashMap<>();
        
        // Copies the attributes of ever type in the new hashmap
        for(int i=0;i<foodTypesList.size();i++) {
        	
        	FoodType singleType = foodTypesList.get(i);
        	listWithoutDuplicates.put(singleType.getCategory(), singleType.getIdFoodType());
        	
        }
        
        // Clears the list
        foodTypesList.clear();
        
        // Iterates through the hashmap that contains unique objects
        Iterator iterator = listWithoutDuplicates.entrySet().iterator();
        while (iterator.hasNext()) {
            
        	Map.Entry pair = (Map.Entry)iterator.next();
            
        	// Creates a new type and assigns the attributes extracted from the hashmap
        	FoodType singleType = new FoodType();
        	singleType.setCategory((String)pair.getKey());
        	singleType.setIdFoodType((Long)pair.getValue());
        	
            iterator.remove();
            
            foodTypesList.add(singleType);
        }
        
        
        // Returns the list of food types (without duplicates)
		return Utilities.throwOK(foodTypesListToReturn);
		
	}
	
	
	@Path("/")
	@POST
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createFood(Food foodToSave){

		Foods fs = ServicesLocator.getFoodServiceConnection();
		
		Food newFood = new Food();
		
		// Tries to save the food passed in input
        if(foodToSave.getName()!=null) {
        	
        	Holder<Food> foodHolder = new Holder<Food>(foodToSave);
            fs.createFood(foodHolder);
            newFood = foodHolder.value;
        	
        }
        
        // Return the new food saved in the database
		return Utilities.throwOK(newFood);
		
	}
	
}
