package com.company;

import com.company.controller.MainController;
import com.company.entities.Category;
import com.company.entities.Recipe;
import com.company.entities.Role;
import com.company.entities.User;

import java.util.List;
import java.util.Scanner;

public class MyApplication {
    private final MainController controller;
    private final Scanner scanner;

    public MyApplication(MainController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    /*
     *******************************************************************************************************
     *This method displays 3 menu options on the console
     *******************************************************************************************************
     */
    public void run() {
        boolean loginRegistrationChoice = true;
        int loginRegistrationOption = 0;

        while (true) {
            if (loginRegistrationChoice) {
                System.out.println("Please, choose one option:\n1)Registration\n2)Login\n3)Exit");
                loginRegistrationOption = scanner.nextInt();
            }

            if (loginRegistrationOption == 1) {
                boolean registered = registrationMode();
                if (registered) {
                    loginRegistrationChoice = false;
                    loginRegistrationOption = 2;
                }
            } else if (loginRegistrationOption == 2) {//if the user is registered then he will go to login
                User loggedUser = loginMode();
                if (loggedUser != null) {
                    if (loggedUser.getRole().getName().equals("user")) {
                        userMode(loggedUser);
                    } else {
                        adminMode(loggedUser);
                    }
                    loginRegistrationChoice = true;
                }
            } else {
                break;
            }
        }
    }


    /*
     *******************************************************************************************************
     *This method registers a new user
     *******************************************************************************************************
     */
    private boolean registrationMode() {
        System.out.println("***Registration***");
        //requesting all data about user
        System.out.println("Write name:");
        String name = scanner.next();
        System.out.println("Write surname:");
        String surname = scanner.next();
        System.out.println("Write username with length more than 14 and it must end with '@gmail.com':");
        String username = scanner.next();

        if (controller.getUser(username) != null) {
            System.out.println("This username is used! Please, write another username!");
            return false;
        }//check if there is a similar user with the same username in the database(because username is unique)


        System.out.println("Write password with length more than 7 and it must contain at least 1 lowercase letter, 1 uppercase letter and 1 digit:");
        String password = scanner.next();


        if (isValid(username, password)) {//checks validity of username and password. If they are in correct form the data sends into controller. Then they will be saved.
            return controller.registration(name, surname, username, securePassword(password));
            //passing all the data to the controller
        }
        return false;

    }

    /*
     *******************************************************************************************************
     *This method check username and password
     *******************************************************************************************************
     */
    private boolean isValid(String username, String password) {
        if (username.length()<15) {
            System.out.println("Your username is too short!");
            return false;
        }
        if (!username.endsWith("@gmail.com")) {
            System.out.println("Your username must end with '@gmail.com'");
            return false;
        }
        boolean upperCase = false, lowerCase = false, digit = false;
        for (int i=0; i<password.length(); i++) {
            if (password.charAt(i)>=65 && password.charAt(i)<=90) {
                upperCase = true;
            }
            if (password.charAt(i)>=97 && password.charAt(i)<=122) {
                lowerCase = true;
            }
            if (password.charAt(i)>=48 && password.charAt(i)<=57) {
                digit = true;
            }
        }
        if (!upperCase) {
            System.out.println("Your username must contain at least 1 uppercase letter!");
        }
        if (!lowerCase) {
            System.out.println("Your username must contain at least 1 lowercase letter!");
        }
        if (!digit) {
            System.out.println("Your username must contain at least 1 digit!");
        }
        if (upperCase && lowerCase && digit) {
            return true;
        }
        return false;
    }
    public String securePassword(String password) {
        return Integer.toString(password.hashCode());
    }//this method encodes password and returns it


    /*
     *******************************************************************************************************
     *This method login user
     *******************************************************************************************************
     */
    private User loginMode() {
        System.out.println("***Login***");
        System.out.println("Write username:");
        String username = scanner.next();
        System.out.println("Write password:");
        String password = scanner.next();

        return controller.login(username, securePassword(password));
    }


    /*
     *******************************************************************************************************
     *This method display user's options menu
     *******************************************************************************************************
     */
    private void userMode(User user) {
        System.out.println("Hello, " + user.getName());

        while (true) {
            System.out.println("Please, choose one option:\n1)My Profile\n2)Update my Profile\n3)Delete my Profile\n4)Recipes\n5)Save recipe\n6)Delete recipe\n7)Saved Recipes\n8)Exit");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println(user.toString());
            } else if (choice == 2) {
                System.out.println("Write name:");
                String name = scanner.next();
                System.out.println("Write surname:");
                String surname = scanner.next();
                System.out.println("Write username:");
                String username = scanner.next();
                if (controller.getUser(username) != null) {
                    System.out.println("This username is used! Please, write another username!");
                    break;
                }
                System.out.println("Write password:");
                String password = scanner.next();

                user.setName(name);
                user.setSurname(surname);
                user.setUsername(username);
                user.setPassword(password);

                controller.updateProfile(user);
            } else if (choice == 3) {
                controller.deleteProfile(user.getId());
                break;
            } else if (choice == 4) {
                recipesMode();
            } else if (choice == 5) {
                List<Recipe> recipeList = controller.getRecipes();

                for (Recipe recipe : recipeList) {
                    System.out.println(recipe.toString());
                }

                System.out.println("Choose any recipe! Write recipe's id!");
                int id = scanner.nextInt();

                controller.addRecipeToSavedRecipeList(user.getId(), id);
            } else if (choice == 6) {
                System.out.println("Your saved recipes:");

                List<Recipe> recipeList = controller.savedRecipes(user.getId());

                for (Recipe recipe : recipeList) {
                    System.out.println(recipe.toString());
                }

                System.out.println("Choose any recipe! Write recipe's id!");
                int id = scanner.nextInt();

                controller.deleteRecipeFromSavedRecipeList(user.getId(), id);
            } else if (choice == 7) {
                System.out.println("Your saved recipes:");

                List<Recipe> recipeList = controller.savedRecipes(user.getId());

                for (Recipe recipe : recipeList) {
                    System.out.println(recipe.toString());
                }
            } else {
                break;
            }
        }
    }


    /*
     *******************************************************************************************************
     *This method display admin's option menu
     *******************************************************************************************************
     */
    private void adminMode(User user) {
        System.out.println("Hello, " + user.getName());

        while (true) {
            System.out.println("Please, choose one option:\n1)My Profile\n2)Update my Profile\n3)Delete my Profile\n4)Recipes\n5)Save recipe\n6)Delete recipe\n7)Saved Recipes\n8)List of Users\n9)Add User\n10)Update User\n11)Delete User\n12)Add Recipe\n13)Exit");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println(user.toString());
            } else if (choice == 2) {
                System.out.println("Write name:");
                String name = scanner.next();
                System.out.println("Write surname:");
                String surname = scanner.next();
                System.out.println("Write username:");
                String username = scanner.next();
                if (controller.getUser(username) != null) {
                    System.out.println("This username is used! Please, write another username!");
                    break;
                }
                System.out.println("Write password:");
                String password = scanner.next();

                user.setName(name);
                user.setSurname(surname);
                user.setUsername(username);
                user.setPassword(password);

                controller.updateProfile(user);
            } else if (choice == 3) {
                controller.deleteProfile(user.getId());
                break;
            } else if (choice == 4) {
                recipesMode();
            } else if (choice == 5) {
                List<Recipe> recipeList = controller.getRecipes();

                for (Recipe recipe : recipeList) {
                    System.out.println(recipe.toString());
                }

                System.out.println("Choose any recipe! Write recipe's id!");
                int id = scanner.nextInt();

                controller.addRecipeToSavedRecipeList(user.getId(), id);
            } else if (choice == 6) {
                System.out.println("Your saved recipes:");

                List<Recipe> recipeList = controller.savedRecipes(user.getId());

                for (Recipe recipe : recipeList) {
                    System.out.println(recipe.toString());
                }

                System.out.println("Choose any recipe! Write recipe's id!");
                int id = scanner.nextInt();

                controller.deleteRecipeFromSavedRecipeList(user.getId(), id);
            } else if (choice == 7) {
                System.out.println("Your saved recipes:");

                List<Recipe> recipeList = controller.savedRecipes(user.getId());

                for (Recipe recipe : recipeList) {
                    System.out.println(recipe.toString());
                }
            } else if (choice == 8) {
                System.out.println("Users:");

                List<User> userList = controller.getListOfUsers();

                for (User u : userList) {
                    System.out.println(u.toString());
                }
            } else if (choice == 9) {
                System.out.println("Write name:");
                String name = scanner.next();
                System.out.println("Write surname:");
                String surname = scanner.next();
                System.out.println("Write username:");
                String username = scanner.next();
                if (controller.getUser(username) != null) {
                    System.out.println("This username is used! Please, write another username!");
                }
                System.out.println("Write password:");
                String password = scanner.next();

                System.out.println("Roles:");
                List<Role> roles = controller.roles();

                for (Role role : roles) {
                    System.out.println(role.toString());
                }

                System.out.println("Please, choose one role! Write id of role!");
                int id = scanner.nextInt();

                Role role = controller.getRole(id);

                controller.addUser(name, surname, username, password, role);
            } else if (choice == 10) {
                System.out.println("Write name:");
                String name = scanner.next();
                System.out.println("Write surname:");
                String surname = scanner.next();
                System.out.println("Write username:");
                String username = scanner.next();
                if (controller.getUser(username) != null) {
                    System.out.println("This username is used! Please, write another username!");
                }
                System.out.println("Write password:");
                String password = scanner.next();

                System.out.println("Roles:");
                List<Role> roles = controller.roles();

                for (Role role : roles) {
                    System.out.println(role.toString());
                }

                System.out.println("Please, choose one role! Write id of role!");
                int id = scanner.nextInt();

                Role role = controller.getRole(id);

                user.setName(name);
                user.setSurname(surname);
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(role);

                controller.updateProfile(user);
            } else if (choice == 11) {
                System.out.println("Users:");

                List<User> userList = controller.getListOfUsers();

                for (User u : userList) {
                    System.out.println(u.toString());
                }

                System.out.println("Please, choose one user to delete! Write user's id!");
                int id = scanner.nextInt();

                controller.deleteProfile(id);
            } else if (choice == 12) {
                System.out.println("Write title!");
                String title = scanner.next();
                System.out.println("Write ingredients!");
                String ingredients = scanner.next();
                System.out.println("Write description!");
                String description = scanner.next();
                System.out.println("Write calories!");
                int calories = scanner.nextInt();
                System.out.println("Write proteins!");
                int proteins = scanner.nextInt();
                System.out.println("Write fats!");
                int fats = scanner.nextInt();
                System.out.println("Write carbohydrates!");
                int carbohydrates = scanner.nextInt();

                List<Category> categoryList = controller.getCategories();

                System.out.println("Categories:");

                for (Category category : categoryList) {
                    System.out.println(category.toString());
                }

                System.out.println("Please, choose one category! Write id of category!");

                int category_id = scanner.nextInt();

                controller.addRecipe(title, ingredients, description, calories, proteins, fats, carbohydrates, category_id);
            } else {
                break;
            }
        }
    }


    /*
     *******************************************************************************************************
     *This method display list of recipes and search recipes
     *******************************************************************************************************
     */
    private void recipesMode() {
        System.out.println("Please, choose any option:\n1)All Recipes\n2)Recipes by Category\n3)Recipes by Title\n4)Recipes with calories less\n5)Recipes with calories more");
        int choice = scanner.nextInt();

        if (choice == 1) {
            List<Recipe> recipeList = controller.getRecipes();
            for (Recipe recipe : recipeList) {
                System.out.println(recipe.toString());
            }
        } else if (choice == 2) {
            List<Category> categoryList = controller.getCategories();
            for (Category category : categoryList) {
                System.out.println(category.toString());
            }

            System.out.println("Choose any category! Write id of Category!");
            int id = scanner.nextInt();

            Category category = controller.getCategory(id);

            System.out.println("You chose category " + category.getName());

            List<Recipe> recipeList = controller.getRecipes(category);
            for (Recipe recipe : recipeList) {
                System.out.println(recipe.toString());
            }
        } else if (choice == 3) {
            System.out.println("Write title");
            String title = scanner.next();

            List<Recipe> recipeList = controller.getRecipes(title);
            for (Recipe recipe : recipeList) {
                System.out.println(recipe.toString());
            }
        } else if (choice == 4) {
            System.out.println("Write calories");
            int calories = scanner.nextInt();

            List<Recipe> recipeList = controller.getRecipesLessCalories(calories);
            for (Recipe recipe : recipeList) {
                System.out.println(recipe.toString());
            }
        } else if (choice == 5) {
            System.out.println("Write calories");
            int calories = scanner.nextInt();

            List<Recipe> recipeList = controller.getRecipesMoreCalories(calories);
            for (Recipe recipe : recipeList) {
                System.out.println(recipe.toString());
            }
        }
    }
}
