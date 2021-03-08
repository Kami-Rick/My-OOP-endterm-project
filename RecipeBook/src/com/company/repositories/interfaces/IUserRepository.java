package com.company.repositories.interfaces;

import com.company.entities.Recipe;
import com.company.entities.User;

import java.util.List;

public interface IUserRepository {
    public boolean addUser(User user);
    public List<User> allUsers();
    public User getUser(int id);
    public User getUser(String username);
    public boolean saveRecipe(int userId, int recipeId);
    public boolean updateUser(User user);
    public List<Recipe> savedRecipes(int id);
    public boolean deleteUser(int id);
    public boolean deleteRecipeFromSavedRecipes(int userId, int recipeId);
}
