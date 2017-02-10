package myhealthylife.centric2.rest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import myhealthylife.centric2.dao.CentricServiceDao;
import myhealthylife.centric2.util.ServicesLocator;
import myhealthylife.nutritionservice.soap.Food;
import myhealthylife.nutritionservice.soap.Foods;

@Entity
@Table(name="recipes")
@NamedQuery(name="Recipe.findAll", query="SELECT r FROM Recipe r")
@XmlRootElement(name="recipe")
public class Recipe {

	@Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(strategy=GenerationType.AUTO) 
    @Column(name="recipeId") // maps the following attribute to a column
    private long recipeId;
	
	private List<Long> ingredientsIDs;
	
	private String name;
	
	private String description;
	
	/**
	 * this list will not saved in the DB, only the IDs, foods are already stored in the Service04
	 */
	@Transient
	private List<Food> ingredients;

	public long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}

	@XmlTransient
	public List<Long> getIngredientsIDs() {
		
		/*only the ids will be saved in the DB, calculate the id when someone asking for it*/
		ingredientsIDs=new ArrayList<Long>();
		
		if(ingredients!=null){
			Iterator<Food> it=ingredients.iterator();
			
			while(it.hasNext()){
				Food f=it.next();
				
				ingredientsIDs.add(f.getIdFood());
			}
		}
		return ingredientsIDs;
	}

	public void setIngredientsIDs(List<Long> ingredientsIDs) {
		this.ingredientsIDs = ingredientsIDs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static List<Recipe> getAll(){
		EntityManager em=CentricServiceDao.instance.createEntityManager();
		List<Recipe> list=em.createNamedQuery("Recipe.findAll", Recipe.class).getResultList();
		CentricServiceDao.instance.closeConnections(em);
		return list;
	}

	@Transient
	public List<Food> getIngredients() {
		
		/* when someone asking for the ingredients list centric02 retrieves the list 
		 * the food date from the service03 based on the ids */
		
		if(ingredients==null)
			ingredients=new ArrayList<Food>();
		
		if(getIngredientsIDs()==null)
			return ingredients;
		
		Foods fs=ServicesLocator.getFoodServiceConnection();
		
		
		Iterator<Long> it=ingredientsIDs.iterator();
		
		while(it.hasNext()){
			Long id=it.next();
			Food f=fs.readFood(id);
			
			
			
			ingredients.add(f);
		}
		
		return ingredients;
	}

	public void setIngredients(List<Food> ingredients) {
		this.ingredients = ingredients;
	}
}
