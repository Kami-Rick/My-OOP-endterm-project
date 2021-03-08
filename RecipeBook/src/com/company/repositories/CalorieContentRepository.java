package com.company.repositories;

import com.company.data.interfaces.IDBManager;
import com.company.entities.CalorieContent;
import com.company.repositories.interfaces.ICalorieContentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CalorieContentRepository implements ICalorieContentRepository {
    private final IDBManager manager;

    public CalorieContentRepository(IDBManager manager) {
        this.manager = manager;
    }

    /*
     *******************************************************************************************************
     *This method adds data about the caloric content of the recipe
     *******************************************************************************************************
     */

    @Override
    public int addCalorieContent(CalorieContent calorieContent) {
        try {
            Connection connection = manager.getConnection();//using the manager we take the connection
            PreparedStatement preparedStatement = connection.prepareStatement("insert into calorie_content (calories, proteins, fats, carbohydrates) values (?,?,?,?) returning id");
            //when we insert some data to database it returns id of this data

            preparedStatement.setInt(1, calorieContent.getCalories());
            preparedStatement.setInt(2, calorieContent.getProteins());
            preparedStatement.setInt(3, calorieContent.getFats());
            preparedStatement.setInt(4, calorieContent.getCarbohydrates());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;//if it returns 0 it means we didn't add calorie content
    }
}
