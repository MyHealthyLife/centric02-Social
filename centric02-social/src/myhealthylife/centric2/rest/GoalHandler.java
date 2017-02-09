package myhealthylife.centric2.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import myhealthylife.centric2.rest.model.Goal;
import myhealthylife.centric2.rest.model.GoalList;
import myhealthylife.centric2.util.ServicesLocator;
import myhealtylife.optimalparamters.soap.OptimalParameters;
import myhealtylife.optimalparamters.soap.Parameter;
import myhealtylife.optimalparamters.soap.ParametersList;

@Path("/goals")
public class GoalHandler {

	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public GoalList getGoals(@QueryParam("sex")String sex, @QueryParam("ageFrom")Integer ageFrom, @QueryParam("ageTo") Integer ageTo ){
		
		GoalList gl=new GoalList();
		OptimalParameters op=ServicesLocator.getOptimalParameterConnection();
		
		if(sex==null){
			if(ageFrom==null && ageTo==null){
				List<Parameter> param= op.readOptimalParameters().getParameters();
				gl.setGoals(param);
				
			}
			else{
				if(ageFrom==null){
					ageFrom=0;
				}
				
				if(ageTo==null){
					ageTo=200;
				}
				
				List<Parameter> paramM=op.readOptimalParametersBySexAngAgeRange("M", ageFrom, ageTo).getParameters();
				List<Parameter> paramF=op.readOptimalParametersBySexAngAgeRange("F", ageFrom, ageTo).getParameters();
				ArrayList<Parameter> tot=new ArrayList<Parameter>();
				tot.addAll(paramM);
				tot.addAll(paramF);
				
				gl.setGoals(tot);
			}
		}else {
			if(ageFrom==null && ageTo==null){
				List<Parameter> param=op.readOptimalParametersBySex(sex).getParameters();
				gl.setGoals(param);
			}
			else{
				if(ageFrom==null){
					ageFrom=0;
				}
				
				if(ageTo==null){
					ageTo=200;
				}
				
				
				List<Parameter> list=op.readOptimalParametersBySexAngAgeRange(sex, ageFrom, ageTo).getParameters();
				gl.setGoals(list);
			}
		}
		
		return gl;
		
	}
	
	@POST
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	/**
	 * create a new goal (a.k.a. a parameter for the service03)
	 * @param p
	 * @return
	 */
	public Parameter createNewGoal(Parameter p){
		OptimalParameters op=ServicesLocator.getOptimalParameterConnection();
		p=op.createParameter(p);
		return p;
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
