package myhealthylife.centric2.rest;

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
import myhealthylife.nutritionservice.soap.Foods;
import myhealthylife.nutritionservice.soap.Foods_Type;

@Path("/food")
public class FoodHandler {

	@Path("/{type}")
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getFoodsByTypeAndCalories(@PathParam("type") String typeName, @QueryParam("maxCal") Integer maxCal){

		Foods fs = ServicesLocator.getFoodServiceConnection();
		
		// Gets the foods to return
        Foods_Type foodListToReturn = fs.findFoodByTypeFiltered(typeName, maxCal);
        
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
        Foods_Type foodListToReturn = fs.readFoodList();
        
        // Returns the list of foods
		return Utilities.throwOK(foodListToReturn);
		
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
