package myhealthylife.centric2.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
		
		return rl;
	}

}
