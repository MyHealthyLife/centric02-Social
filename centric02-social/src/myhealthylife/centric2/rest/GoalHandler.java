package myhealthylife.centric2.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import myhealthylife.centric2.rest.model.Goal;
import myhealthylife.centric2.rest.model.GoalList;
import myhealthylife.centric2.util.ServicesLocator;
import myhealtylife.optimalparamters.soap.OptimalParameters;
import myhealtylife.optimalparamters.soap.Parameter;

@Path("/goal")
public class GoalHandler {

	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public GoalList getGoals(@QueryParam("sex")String sex, @QueryParam("ageFrom")String ageTo, @QueryParam("ageTo") String ageFrom ){
		
		GoalList gl=new GoalList();
		OptimalParameters op=ServicesLocator.getOptimalParameterConnection();
		
		if(sex==null){
			List<Parameter> param= op.readOptimalParameters().getParameters();
			gl.setGoals(paramToGoal(param));
		}else {
			if(ageFrom==null && ageTo==null){
				//gl.setGoals(paramToGoal(op.read));
			}
		}
		
		return gl;
		
	}
	
	private List<Goal> paramToGoal(List<Parameter> param){
		
		ArrayList<Goal> goals=new ArrayList<Goal>();
	
		Iterator<Parameter> it=param.iterator();
		
		while(it.hasNext()){
			Parameter p=it.next();
			Goal g=new Goal();
			g.setValueToReach(p.getValue());
			g.setGoalName(p.getParameterName());
			g.setSex(p.getSex());
			goals.add(g);
			
		}
		
		return goals;
	}
	
}
