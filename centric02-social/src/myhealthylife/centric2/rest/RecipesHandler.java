package myhealthylife.centric2.rest;

import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import myhealthylife.centric2.rest.model.Recipe;
import myhealthylife.centric2.rest.model.RecipeList;

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
			it.next().computeFoods();
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
		return r;
	}

}
