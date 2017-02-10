package myhealthylife.centric2.rest;

import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import myhealthylife.centric2.rest.model.Recipe;
import myhealthylife.centric2.rest.model.RecipeList;
import myhealthylife.centric2.util.Utilities;

@Path("/recipe")
public class RecipesHandler {
	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public RecipeList hello(){
		RecipeList rl=new RecipeList();
		rl.setRecipes(Recipe.getAll());
		Iterator<Recipe> it=rl.getRecipes().iterator();
		
		while(it.hasNext()){
			Recipe r=it.next();
			r.computeFoods();
			r.computeCalories();
		}
		
		//TODO calories
		
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

}
