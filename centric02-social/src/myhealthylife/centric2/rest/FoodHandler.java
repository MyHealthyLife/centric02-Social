package myhealthylife.centric2.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import myhealthylife.centric2.util.ServicesLocator;
import myhealthylife.centric2.util.Utilities;
import myhealthylife.nutritionservice.soap.FoodList;
import myhealthylife.nutritionservice.soap.Foods;

@Path("/food")
public class FoodHandler {

	@Path("/{type}")
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public FoodList getFoodsByTypeAndCalories(@PathParam("type") String typeName){

		Foods fs = ServicesLocator.getFoodServiceConnection();
		
		// Gets the foods to return
        FoodList foodListToReturn = fs.findFoodByTypeFilteredByCalories(maxCal);
        
        if(foodListToReturn==null) {
        	foodListToReturn = fs.readFoodList();
        }
        
        // Returns the random sentence
		return Utilities.throwOK(foodListToReturn);
		
	}
	
}
