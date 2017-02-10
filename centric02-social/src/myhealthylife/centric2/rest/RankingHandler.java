package myhealthylife.centric2.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import myhealthylife.centric2.util.ServicesLocator;
import myhealthylife.centric2.util.Utilities;
import myhealthylife.dataservice.soap.DataService;
import myhealthylife.dataservice.soap.Person;
import myhealthylife.nutritionservice.soap.FoodList;
import myhealthylife.nutritionservice.soap.Foods;


@Path("/ranking")
public class RankingHandler {

	
	@Path("/{username}")
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getUserRank(@PathParam("username") String username){

		DataService ds = ServicesLocator.getDataServiceConnection();
		
		// Gets the user from service 1
		Person user = ds.getPersonByUsername(username);
		
        // Returns
		return Utilities.throwOK(user);
		
	}
	
}
