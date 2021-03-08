package com.company.repositories;

import com.company.data.interfaces.IDBManager;
import com.company.entities.*;
import com.company.repositories.interfaces.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositories implements IUserRepository {
    private final IDBManager manager;

    public UserRepositories(IDBManager manager) {
        this.manager = manager;
    }


    /*
     *******************************************************************************************************
     *This method adds a new user (only admin can do this).
     *******************************************************************************************************
     */
    @Override
    public boolean addUser(User user) {
        try {
            Connection connection = manager.getConnection();//using the manager we take the connection

            PreparedStatement preparedStatement = connection.prepareStatement("insert into \"user\" (name, surname, username, password, role_id) values (?,?,?,?,?)");
            //creating a PreparedStatement object, applying the method of the Connection class's prepareStatement (), passing the sql INSERT INTO

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getRole().getId());
            //data is entered from the console and then added to the database

            preparedStatement.execute();//run the request (method execute (): executes any SQL request)

            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }


    /*
     *******************************************************************************************************
     *This method displays the entire list of users for the admin
     *******************************************************************************************************
     */

    @Override
    public List<User> allUsers() {
        try {
            Connection connection = manager.getConnection();//using the manager we take the connection
            PreparedStatement preparedStatement = connection.prepareStatement("select \"user\".id, \"user\".name, \"user\".surname, \"user\".username, \"user\".password, role.id, role.name from \"user\" inner join role on \"user\".role_id = role.id");
            //this sql query outputs all the user fields and joins the two tables (user and role by role_id) using inner join

            ResultSet resultSet = preparedStatement.executeQuery();
            //the executeQuery () method: executes the SELECT command, which returns data as a ResultSet

            List<User> userList = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        new Role(resultSet.getInt(6), resultSet.getString(7)));
                //loop which shows how many rows returned

                userList.add(user);
            }

            return userList;

        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /*
     *******************************************************************************************************
     *This method displays all profile data users by the index we send
     *******************************************************************************************************
     */

    @Override
    public User getUser(int id) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select \"user\".id, \"user\".name, \"user\".surname, \"user\".username, \"user\".password, role.id, role.name from \"user\" inner join role on \"user\".role_id = role.id where \"user\".id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        new Role(resultSet.getInt(6), resultSet.getString(7)));

                return user;
            }
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /*
     *******************************************************************************************************
     *This method can change the user name ( this method does not change the user name if such a user name already exists )
     *******************************************************************************************************
     */

    public User getUser(String username) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select \"user\".id, \"user\".name, \"user\".surname, \"user\".username, \"user\".password, role.id, role.name from \"user\" inner join role on \"user\".role_id = role.id where \"user\".username=?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        new Role(resultSet.getInt(6), resultSet.getString(7)));

                return user;
            }
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }



    /*
     *******************************************************************************************************
     *This method saves recipes by their index to the user profile
     *******************************************************************************************************
     */

    @Override
    public boolean saveRecipe(int userId, int recipeId) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into user_recipe (user_id, recipe_id) values (?,?)");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recipeId);
            preparedStatement.execute();

            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }


    /*
     *******************************************************************************************************
     *This method updates the profile settings and does not change the user's index (only the admin can change the roles)
     *******************************************************************************************************
     */

    @Override
    public boolean updateUser(User user) { // User(id, name, surname, username, password, Role(id, name))
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update \"user\" set name=?, surname=?, username=?, password=?, role_id=? where id=?");
            //this sql query changes the user parameters (if you need to update only one parameter, then all the others will remain unchanged )

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }


    /*
     *******************************************************************************************************
     *This method shows a list of recipes that the user has saved
     *******************************************************************************************************
     */

    @Override
    public List<Recipe> savedRecipes(int id) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select recipe.id, recipe.title, recipe.ingredients, recipe.description, calorie_content.id, calorie_content.calories, calorie_content.proteins, calorie_content.fats, calorie_content.carbohydrates, category.id, category.name from recipe inner join calorie_content on recipe.calorie_content_id=calorie_content.id inner join category on recipe.category_id=category.id inner join user_recipe on recipe.id=user_recipe.recipe_id inner join \"user\" on user_recipe.user_id=\"user\".id where \"user\".id=?");
            //in this sql query,connects the user id with the recipe id, finds all the recipes that are connected to the users, then connects all the recipes with the category and calorie_content tables


            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe> recipeList = new ArrayList<>();

            while (resultSet.next()) {//creating a new object from the recipe
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
     *This method deletes user profile with all data
     *******************************************************************************************************
     */

    @Override
    public boolean deleteUser(int id) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from user where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    /*
     *******************************************************************************************************
     *This method removes the recipes by the index , that we send, from the user's list of saved recipes.
     *******************************************************************************************************
     */

    @Override
    public boolean deleteRecipeFromSavedRecipes(int userId, int recipeId) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from user_recipe where user_id=? and recipe_id=?");
            //looking for the user index and the recipe index if they match
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recipeId);

            preparedStatement.execute();//then we need to run the query

            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }
}
