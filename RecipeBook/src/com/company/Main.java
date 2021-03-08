package com.company;

import com.company.controller.MainController;
import com.company.data.DBManager;
import com.company.data.interfaces.IDBManager;
import com.company.repositories.*;
import com.company.repositories.interfaces.*;

public class Main {
    public static void main(String[] args) {
        IDBManager manager = new DBManager();
        ICategoryRepository categoryRepository = new CategoryRepository(manager);
        ICalorieContentRepository calorieContentRepository = new CalorieContentRepository(manager);
        IRecipeRepository recipeRepository = new RecipeRepository(manager);
        IRoleRepository roleRepository = new RoleRepository(manager);
        IUserRepository userRepository = new UserRepositories(manager);
        MainController controller = new MainController(userRepository, recipeRepository, categoryRepository, roleRepository, calorieContentRepository);
        MyApplication application = new MyApplication(controller);

        application.run();
    }
}
