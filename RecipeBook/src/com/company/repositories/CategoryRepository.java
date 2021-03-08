package com.company.repositories;

import com.company.data.interfaces.IDBManager;
import com.company.entities.Category;
import com.company.repositories.interfaces.ICategoryRepository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements ICategoryRepository {
    private final IDBManager manager;

    public CategoryRepository(IDBManager manager) {
        this.manager = manager;
    }

    /*
     *******************************************************************************************************
     *This method displays categories by the index we send
     *******************************************************************************************************
     */

    public Category getCategory(int id) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from category where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            //corresponds to a SELECT command that returns data as a ResultSet

            if (resultSet.next()) {
                Category category = new Category(resultSet.getInt("id"), resultSet.getString("name"));
                return category;
            }
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /*
     *******************************************************************************************************
     *This method displays categories by the category name we send (looking for)
     *******************************************************************************************************
     */

    public Category getCategory(String name) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from category where name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Category category = new Category(resultSet.getInt("id"), resultSet.getString("name"));
                return category;
            }
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /*
     *******************************************************************************************************
     *This method lists all recipe categories
     *******************************************************************************************************
     */

    public List<Category> allCategories() {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from category");
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Category> categories = new ArrayList<>();

            while (resultSet.next()) {//goes through the loop, from the first to each element of the categories
                Category category = new Category(resultSet.getInt("id"), resultSet.getString("name"));
                //new object from categories
                categories.add(category);
            }

            return categories;
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
