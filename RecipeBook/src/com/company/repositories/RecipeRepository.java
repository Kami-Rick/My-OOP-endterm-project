package com.company.repositories;

import com.company.data.interfaces.IDBManager;
import com.company.entities.CalorieContent;
import com.company.entities.Category;
import com.company.entities.Recipe;
import com.company.repositories.interfaces.IRecipeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository implements IRecipeRepository {
    private final IDBManager manager;

    public RecipeRepository(IDBManager manager) {
        this.manager = manager;
    }

    /*
     *******************************************************************************************************
     *This method adds new recipes
     *******************************************************************************************************
     */

    @Override
    public boolean addRecipe(Recipe recipe) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into recipe (title, ingredients, description, calorie_content_id, category_id) values (?,?,?,?,?)");
            //in this sql query, we fill in all the fields of the recipe table

            preparedStatement.setString(1, recipe.getTitle());
            preparedStatement.setString(2, recipe.getIngredients());
            preparedStatement.setString(3, recipe.getDescription());
            preparedStatement.setInt(4, recipe.getCalorieContent().getId());
            preparedStatement.setInt(5, recipe.getCategory().getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    /*
     *******************************************************************************************************
     *This method removes recipes
     *******************************************************************************************************
     */

    @Override
    public boolean deleteRecipe(int id) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from recipe where id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            return true;
        }catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }


    /*
     *******************************************************************************************************
     *This method displays recipes by the index we send
     *******************************************************************************************************
     */

    @Override
    public Recipe getRecipe(int id) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select recipe.id, recipe.title, recipe.ingredients, recipe.description, calorie_content.id, calorie_content.calories, calorie_content.proteins, calorie_content.fats, calorie_content.carbohydrates, category.id, category.name from recipe inner join calorie_content on recipe.calorie_content_id=calorie_content.id inner join category on recipe.category_id=category.id where recipe.id=?");
            //in this sql query, finds all the recipe table fields by recipe index, then connects the recipes with the category and calorie_content tables
            preparedStatement.setInt(1, id);//passing the value

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Recipe recipe = new Recipe(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        new CalorieContent(resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9)),
                        new Category(resultSet.getInt(10), resultSet.getString(11)));

                return recipe;
            }
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }


    /*
     *******************************************************************************************************
     *This method displays the recipe by title we wrote
     *******************************************************************************************************
     */

    @Override
    public List<Recipe> getRecipes(String title) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select recipe.id, recipe.title, recipe.ingredients, recipe.description, calorie_content.id, calorie_content.calories, calorie_content.proteins, calorie_content.fats, calorie_content.carbohydrates, category.id, category.name from recipe inner join calorie_content on recipe.calorie_content_id=calorie_content.id inner join category on recipe.category_id=category.id where recipe.title like '%" + title + "%'");
            //This sql query outputs all the fields of the recipe that we are looking for by title
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe> recipeList = new ArrayList<>();

            while (resultSet.next()) {
                Recipe recipe = new Recipe(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        new CalorieContent(resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9)),
                        new Category(resultSet.getInt(10), resultSet.getString(11)));

                recipeList.add(recipe);
            }

            return recipeList;
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }


    /*
     *******************************************************************************************************
     *This method returns all recipes
     *******************************************************************************************************
     */

    @Override
    public List<Recipe> allRecipes() {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select recipe.id, recipe.title, recipe.ingredients, recipe.description, calorie_content.id, calorie_content.calories, calorie_content.proteins, calorie_content.fats, calorie_content.carbohydrates, category.id, category.name from recipe inner join calorie_content on recipe.calorie_content_id=calorie_content.id inner join category on recipe.category_id=category.id");
            //in this sql query, display all recipe table fields and connects the recipes with the category and calorie_content tables
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe> recipeList = new ArrayList<>();

            while (resultSet.next()) {
                Recipe recipe = new Recipe(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        new CalorieContent(resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9)),
                        new Category(resultSet.getInt(10), resultSet.getString(11)));

                recipeList.add(recipe);
            }

            return recipeList;
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /*
    ********************************************************************************************************************
    *This method returns us all recipes with category we send.
    ********************************************************************************************************************
     */

    @Override
    public List<Recipe> allRecipesByCategory(Category category) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select recipe.id, recipe.title, recipe.ingredients, recipe.description, calorie_content.id, calorie_content.calories, calorie_content.proteins, calorie_content.fats, calorie_content.carbohydrates, category.id, category.name from recipe inner join calorie_content on recipe.calorie_content_id=calorie_content.id inner join category on recipe.category_id=category.id where category.id=?");
            //this sql query returns all recipes by the index of the categories we have selected
            preparedStatement.setInt(1, category.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe> recipeList = new ArrayList<>();

            while (resultSet.next()) {
                Recipe recipe = new Recipe(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        new CalorieContent(resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9)),
                        new Category(resultSet.getInt(10), resultSet.getString(11)));

                recipeList.add(recipe);
            }

            return recipeList;
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /*
    ********************************************************************************************************************
    *This method returns us all recipes which's calories are less than calorie we send.
    ********************************************************************************************************************
     */

    @Override
    public List<Recipe> allRecipesLessCalories(int calories) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select recipe.id, recipe.title, recipe.ingredients, recipe.description, calorie_content.id, calorie_content.calories, calorie_content.proteins, calorie_content.fats, calorie_content.carbohydrates, category.id, category.name from recipe inner join calorie_content on recipe.calorie_content_id=calorie_content.id inner join category on recipe.category_id=category.id where calorie_content.calories<?");

            preparedStatement.setInt(1, calories);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe> recipeList = new ArrayList<>();

            while (resultSet.next()) {
                Recipe recipe = new Recipe(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        new CalorieContent(resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9)),
                        new Category(resultSet.getInt(10), resultSet.getString(11)));

                recipeList.add(recipe);
            }

            return recipeList;
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /*
    ********************************************************************************************************************
    *This method returns us all recipes which's calories are more than calorie we send.
    ********************************************************************************************************************
     */

    @Override
    public List<Recipe> allRecipesMoreCalories(int calories) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select recipe.id, recipe.title, recipe.ingredients, recipe.description, calorie_content.id, calorie_content.calories, calorie_content.proteins, calorie_content.fats, calorie_content.carbohydrates, category.id, category.name from recipe inner join calorie_content on recipe.calorie_content_id=calorie_content.id inner join category on recipe.category_id=category.id where calorie_content.calories>?");

            preparedStatement.setInt(1, calories);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe> recipeList = new ArrayList<>();

            while (resultSet.next()) {
                Recipe recipe = new Recipe(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        new CalorieContent(resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9)),
                        new Category(resultSet.getInt(10), resultSet.getString(11)));

                recipeList.add(recipe);
            }

            return recipeList;
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
