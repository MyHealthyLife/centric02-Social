package myhealthylife.centric2.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


import myhealthylife.centric2.rest.model.GoalList;
import myhealtylife.optimalparamters.soap.OptimalParameters;

@Path("/goal")
public class GoalHandler {

	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public GoalList getGoals(@QueryParam("sex")String sex, @QueryParam("ageFrom")String ageTo, @QueryParam("ageTo") String ageFrom ){
		
		GoalList gl=new GoalList();
		OptimalParameters op;
		
		if(sex==null){
			
		}
		
		return gl;
		
	}
}
