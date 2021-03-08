package com.company.repositories;

import com.company.data.interfaces.IDBManager;
import com.company.entities.Role;
import com.company.repositories.interfaces.IRoleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository implements IRoleRepository {
    private final IDBManager manager;

    public RoleRepository(IDBManager manager) {
        this.manager = manager;
    }


    /*
     *******************************************************************************************************
     *This method displays roles by index we send
     *******************************************************************************************************
     */

    public Role getRole(int id) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from role where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Role role = new Role(resultSet.getInt("id"), resultSet.getString("name"));
                return role;
            }
        }  catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return null;
    }


    /*
     *******************************************************************************************************
     *This method displays roles by name send
     *******************************************************************************************************
     */

    public Role getRole(String name) {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from role where name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Role role = new Role(resultSet.getInt("id"), resultSet.getString("name"));
                return role;
            }
        }  catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    /*
     *******************************************************************************************************
     *This method outputs all fields from the role
     *******************************************************************************************************
     */
    @Override
    public List<Role> getRoles() {
        try {
            Connection connection = manager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from role");
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Role> roleList = new ArrayList<>();

            while (resultSet.next()) {
                Role role = new Role(resultSet.getInt("id"), resultSet.getString("name"));
                roleList.add(role);
            }

            return roleList;
        }  catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
