package com.gmail.zlotnikova.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.zlotnikova.repository.UserInfoRepository;
import com.gmail.zlotnikova.repository.model.User;
import com.gmail.zlotnikova.repository.model.UserInfo;

public class UserInfoRepositoryImpl implements UserInfoRepository {

    private static UserInfoRepository instance;

    private UserInfoRepositoryImpl() {
    }

    public static UserInfoRepository getInstance() {
        if (instance == null) {
            instance = new UserInfoRepositoryImpl();
        }
        return instance;
    }

    @Override
    public UserInfo add(Connection connection, UserInfo userInfo) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user_information VALUES (?, ?, ?)")
        ) {
            statement.setInt(1, userInfo.getUserId());
            statement.setString(2, userInfo.getAddress());
            statement.setString(3, userInfo.getTelephone());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding user information failed, no rows affected");
            }
        }
        return userInfo;
    }

    @Override
    public int deleteUserUnderLimitAge(Connection connection, int age) throws SQLException {
        int rowsDeleted;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM user_information WHERE user_id IN (SELECT id FROM user WHERE age < ?)")
        ) {
            preparedStatement.setInt(1, age);
            rowsDeleted = preparedStatement.executeUpdate();
        }
        return rowsDeleted;
    }

    @Override
    public List<UserInfo> findAll(Connection connection) throws SQLException {
        List<UserInfo> userInfoList = new ArrayList<>();
        try (
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_information");
            while (resultSet.next()) {
                UserInfo userInfo = UserInfo.newBuilder()
                        .userId(resultSet.getInt("user_id"))
                        .address(resultSet.getString("address"))
                        .telephone(resultSet.getString("telephone"))
                        .build();
                userInfoList.add(userInfo);
            }
        }
        return userInfoList;
    }

}
