package myhealthylife.centric2.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import myhealthylife.centric2.rest.model.Goal;
import myhealthylife.centric2.rest.model.GoalList;
import myhealthylife.centric2.util.ServicesLocator;
import myhealtylife.optimalparamters.soap.AgeRangeList;
import myhealtylife.optimalparamters.soap.OptimalParameters;
import myhealtylife.optimalparamters.soap.Parameter;
import myhealtylife.optimalparamters.soap.ParametersList;

@Path("/goals")
public class GoalHandler {

	/**
	 * Returns the goals for each measure type for the age range (and sex) requested by the caller. 
	 * In fact, all the query parameters are required in order to filter the search.
	 * @param sex
	 * @param ageFrom
	 * @param ageTo
	 * @return
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public GoalList getGoals(@QueryParam("sex")String sex, @QueryParam("ageFrom")Integer ageFrom, @QueryParam("ageTo") Integer ageTo ){
		
		GoalList gl=new GoalList();
		OptimalParameters op=ServicesLocator.getOptimalParameterConnection();
		
		if(sex==null){
			/*if sex and age range are not set retrieve all parameters*/
			if(ageFrom==null && ageTo==null){
				List<Parameter> param= op.readOptimalParameters().getParameters();
				gl.setGoals(param);
				
			}
			else{
				
				/*if sex is not set retrive the paramater for the age range for the both sex.*/
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
			/*if the sex is set and age range is not set retrieve all parameter for the sex*/
			if(ageFrom==null && ageTo==null){
				List<Parameter> param=op.readOptimalParametersBySex(sex).getParameters();
				gl.setGoals(param);
			}
			/*else retrieve all parameter filtered for age range and sex*/
			else{
				/*if only the max get is set, set the minimum to 0*/
				if(ageFrom==null){
					ageFrom=0;
				}
				
				/*if only the min age is set, set the maximum to 200*/
				if(ageTo==null){
					ageTo=200;
				}
				
				
				List<Parameter> list=op.readOptimalParametersBySexAngAgeRange(sex, ageFrom, ageTo).getParameters();
				gl.setGoals(list);
			}
		}
		
		return gl;
		
	}
	
	/**
	 *  Returns all the available age ranges memorized in service03. The ranges are returned as AgeRangeList object which contains a list of AgeRange objects.
	 * @return
	 */
	@GET
	@Path("/ageranges")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public AgeRangeList getAgeRanges(){
		return ServicesLocator.getOptimalParameterConnection().readAgeRanges();
	}
	
	/**
	 * create a new goal (a.k.a. a parameter for the service03)
	 * @param p
	 * @return
	 */
	@POST
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Parameter createNewGoal(Parameter p){
		OptimalParameters op=ServicesLocator.getOptimalParameterConnection();
		p=op.createParameter(p);
		return p;
	}
	
	/**
	 * Edits and updates an existing goal in the database. The goal already exists and the updated version will be provided by the caller. 
	 * The changes will be memorized in service03 database. It will eventually return the same object if the update succeeded.
	 * @param p
	 * @return
	 */
	@PUT
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Parameter editGoal(Parameter p){
		OptimalParameters op=ServicesLocator.getOptimalParameterConnection();
		return op.updateParameter(p);
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
