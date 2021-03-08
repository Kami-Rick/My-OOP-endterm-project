package com.company.repositories.interfaces;

import com.company.entities.CalorieContent;
import com.company.entities.Category;
import com.company.entities.Recipe;

import java.util.List;

public interface IRecipeRepository {
    public boolean addRecipe(Recipe recipe);
    public boolean deleteRecipe(int id);
    public Recipe getRecipe(int id);
    public List<Recipe> getRecipes(String title);
    public List<Recipe> allRecipes();
    public List<Recipe> allRecipesByCategory(Category category);
    public List<Recipe> allRecipesLessCalories(int calories);
    public List<Recipe> allRecipesMoreCalories(int calories);
}
