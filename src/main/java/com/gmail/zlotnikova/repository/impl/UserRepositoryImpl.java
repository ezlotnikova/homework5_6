package com.gmail.zlotnikova.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.zlotnikova.repository.UserRepository;
import com.gmail.zlotnikova.repository.model.User;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public User add(Connection connection, User user) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user (username, password, is_active, user_group_id, age) VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.getActive());
            statement.setInt(4, user.getUserGroupId());
            statement.setInt(5, user.getAge());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding user failed, no rows affected");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Adding user failed, no ID obtained");
                }
            }
        }
        return user;
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();
        try (
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User user = User.newBuilder()
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .isActive(resultSet.getBoolean("is_active"))
                        .userGroupId(resultSet.getInt("user_group_id"))
                        .age(resultSet.getInt("age"))
                        .build();
                user.setId(resultSet.getInt("id"));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public int deleteUserUnderLimitAge(Connection connection, int age) throws SQLException {
        int rowsDeleted;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(" DELETE FROM user WHERE age < ?")
        ) {
            preparedStatement.setInt(1, age);
            rowsDeleted = preparedStatement.executeUpdate();
        }
        return rowsDeleted;
    }

    @Override
    public int changeStatusByAge(Connection connection, int lowerAgeLimit, int upperAgeLimit) throws SQLException {
        int rowsAffected;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE user SET is_active = ? WHERE age >= ? AND age <= ? AND is_active = ?")
        ) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, lowerAgeLimit);
            preparedStatement.setInt(3, upperAgeLimit);
            preparedStatement.setBoolean(4, false);
            rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected;
        }
    }

}
