package com.company.controller;

import com.company.entities.*;
import com.company.repositories.interfaces.*;

import java.util.List;

public class MainController {
    //declaring all the repositories that I have
    private final IUserRepository userRepository;
    private final IRecipeRepository recipeRepository;
    private final ICategoryRepository categoryRepository;
    private final IRoleRepository roleRepository;
    private final ICalorieContentRepository calorieContentRepository;


    //constructor with all parameters
    public MainController(IUserRepository userRepository, IRecipeRepository recipeRepository, ICategoryRepository categoryRepository, IRoleRepository roleRepository, ICalorieContentRepository calorieContentRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
        this.calorieContentRepository = calorieContentRepository;
    }

    /*
     *******************************************************************************************************
     *This method registers a new user
     *******************************************************************************************************
     */
    public boolean registration(String name, String surname, String username, String password) {
        User user = new User(name, surname, username, password, new Role(1));
        //adding a new user and passing the parameters

        boolean isAdded = userRepository.addUser(user);

        if (isAdded) {
            System.out.println("You have registered successfully!");
        }// if you added a user
        else {
            System.out.println("Your registration has been failed!");
        }

        return isAdded;
    }



    /*
     *******************************************************************************************************
     *This method login a new user
     *******************************************************************************************************
     */
    public User login(String username, String password) {
        User user = userRepository.getUser(username);
        //looking for a user by username

        if (user.getPassword().equals(password)) {
            //compare two Strings (Using the Method .equals()) the password from the console and the set password
            System.out.println("You logged in successfully!");//output if the passwords are equal
            return user;//save user and send id to profile method
        }

        System.out.println("Your username or password is not correct!");
        //output if the passwords are not equal

        return null;
    }



    /*
     *******************************************************************************************************
     *This method display all profile data
     *******************************************************************************************************
     */
    public User profile(int id) {
        User user = userRepository.getUser(id);
        //looking for a user by id

        System.out.println(user.toString());
        //output all fields using the .tostring() method

        return user;
    }



    /*
     *******************************************************************************************************
     *This method update user profile
     *******************************************************************************************************
     */
    public User updateProfile(User user) {
        boolean isUpdated = userRepository.updateUser(user);

        if (isUpdated) {//
            System.out.println("You have updated profile successfully!");
            return userRepository.getUser(user.getId());
            //the user does not change the id
        }

        System.out.println("Updating has been failed");
        return user;
    }


    /*
     *******************************************************************************************************
     *This method delete user profile
     *******************************************************************************************************
     */
    public boolean deleteProfile(int id) {
        boolean isDeleted = userRepository.deleteUser(id);

        if (isDeleted) {
            System.out.println("You have deleted account successfully!");
            return true;
        }

        System.out.println("Account has not been deleted!");
        return false;
    }


    /*
     *******************************************************************************************************
     *This method return displayed recipe
     *******************************************************************************************************
     */
    public Recipe getRecipe(int id) {
        Recipe recipe = recipeRepository.getRecipe(id);
        //
        return recipe;
    }


    /*
     *******************************************************************************************************
     *This method return displayed recipe by title
     *******************************************************************************************************
     */
    public List<Recipe> getRecipes(String title) {
        List<Recipe> recipeList = recipeRepository.getRecipes(title);

        return recipeList;
    }


    /*
     *******************************************************************************************************
     *This method returns displayed recipes by category
     *******************************************************************************************************
     */
    public List<Recipe> getRecipes(Category category) {
        List<Recipe> recipeList = recipeRepository.allRecipesByCategory(category);

        return recipeList;
    }


    /*
     *******************************************************************************************************
     *This method return recipes with less calories
     *******************************************************************************************************
     */
    public List<Recipe> getRecipesLessCalories(int calories) {
        List<Recipe> recipeList = recipeRepository.allRecipesLessCalories(calories);

        return recipeList;
    }


    /*
     *******************************************************************************************************
     *This method return recipes with more calories
     *******************************************************************************************************
     */
    public List<Recipe> getRecipesMoreCalories(int calories) {
        List<Recipe> recipeList = recipeRepository.allRecipesMoreCalories(calories);

        return recipeList;
    }


    /*
     *******************************************************************************************************
     *This method add recipe to recipe list
     *******************************************************************************************************
     */
    public boolean addRecipeToSavedRecipeList(int userId, int recipeId) {
        boolean isAdded = userRepository.saveRecipe(userId, recipeId);

        if (isAdded) {
            System.out.println("You saved recipe!");
        }
        else {
            System.out.println("You didn't save recipe!");
        }

        return isAdded;
    }


    /*
     *******************************************************************************************************
     *This method delete recipe from user's saved recipes
     *******************************************************************************************************
     */
    public boolean deleteRecipeFromSavedRecipeList(int userId, int recipeId) {
        boolean isDeleted = userRepository.deleteRecipeFromSavedRecipes(userId, recipeId);

        if (isDeleted) {
            System.out.println("You deleted recipe!");
        }
        else {
            System.out.println("You didn't delete recipe!");
        }

        return isDeleted;
    }


    /*
     *******************************************************************************************************
     *This method return list of users
     *******************************************************************************************************
     */
    public List<User> getListOfUsers() {
        List<User> userList = userRepository.allUsers();

        return userList;
    }


    /*
     *******************************************************************************************************
     *This method adds users
     *******************************************************************************************************
     */
    public boolean addUser(String name, String surname, String username, String password, Role role) {
        //passes all user fields
        boolean isAdded = userRepository.addUser(new User(name, surname, username, password, role));

        if (isAdded) {
            System.out.println("You added user!");
        }
        else {
            System.out.println("You didn't add user!");
        }

        return isAdded;
    }


    /*
     *******************************************************************************************************
     *This method adds recipe
     *******************************************************************************************************
     */
    public boolean addRecipe(String title, String ingredients, String description, int calories, int proteins, int fats, int carbohydrates, int category_id) {
        Category category = categoryRepository.getCategory(category_id);
        CalorieContent calorieContent = new CalorieContent(calories, proteins, fats, carbohydrates);
        // add calories
        int calorieContentId = calorieContentRepository.addCalorieContent(calorieContent);
        calorieContent.setId(calorieContentId);
        boolean isAdded = recipeRepository.addRecipe(new Recipe(title, ingredients, description, calorieContent, category));

        if (isAdded) {
            System.out.println("Recipe was added!");
        }
        else {
            System.out.println("Recipe wasn't added!");
        }

        return isAdded;
    }


    /*
     *******************************************************************************************************
     *All other repository methods
     *******************************************************************************************************
     */
    public List<Role> roles() {
        return roleRepository.getRoles();
    }

    public Role getRole(int id) {
        return roleRepository.getRole(id);
    }

    public Role getRole(String name) {
        return roleRepository.getRole(name);
    }

    public Category getCategory(String name) {
        return categoryRepository.getCategory(name);
    }

    public List<Category> getCategories() {
        return categoryRepository.allCategories();
    }

    public List<Recipe> savedRecipes(int id) {
        return userRepository.savedRecipes(id);
    }

    public List<Recipe> getRecipes() {
        return recipeRepository.allRecipes();
    }

    public User getUser(String username) {
        return userRepository.getUser(username);
    }

    public Category getCategory(int id) {
        return categoryRepository.getCategory(id);
    }
}
