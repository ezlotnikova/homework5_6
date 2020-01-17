package com.gmail.zlotnikova.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gmail.zlotnikova.repository.UserGroupRepository;
import com.gmail.zlotnikova.repository.model.UserGroup;
import com.gmail.zlotnikova.service.model.UserGroupDTO;

public class UserGroupRepositoryImpl implements UserGroupRepository {

    private static UserGroupRepository instance;

    private UserGroupRepositoryImpl() {
    }

    public static UserGroupRepository getInstance() {
        if (instance == null) {
            instance = new UserGroupRepositoryImpl();
        }
        return instance;
    }

    @Override
    public UserGroup add(Connection connection, UserGroup userGroup) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user_group (name) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, userGroup.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding user group failed, no rows affected");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userGroup.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Adding user group failed, no ID obtained");
                }
            }
        }
        return userGroup;
    }

    @Override
    public UserGroupDTO showGroupSize(Connection connection, int groupId) throws SQLException {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT ug.name AS name, COUNT(u.username) AS size " +
                                "FROM user AS u JOIN user_group AS ug ON u.user_group_id = ug.id " +
                                "WHERE u.user_group_id = ?")
        ) {
            preparedStatement.setInt(1, groupId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String groupName = resultSet.getString(1);
                    userGroupDTO.setName(groupName);
                    int groupSize = resultSet.getInt("size");
                    userGroupDTO.setSize(groupSize);
                }
            }
        }
        return userGroupDTO;
    }

}