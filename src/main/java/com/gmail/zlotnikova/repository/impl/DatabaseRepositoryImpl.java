package com.gmail.zlotnikova.repository.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.gmail.zlotnikova.repository.DatabaseRepository;
import com.gmail.zlotnikova.util.DatabaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseRepositoryImpl implements DatabaseRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static DatabaseRepository instance;

    private DatabaseRepositoryImpl() {
    }

    public static DatabaseRepository getInstance() {
        if (instance == null) {
            instance = new DatabaseRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void createDatabase(Connection connection) {
        String initFileName = "init.sql";
        String query = DatabaseUtil.getInitData(initFileName);
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
            logger.info("Database created");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void prepareTables(Connection connection) {
        dropUserInfoTable(connection);
        dropUserTable(connection);
        dropUserGroupTable(connection);
        createUserGroupTable(connection);
        createUserTable(connection);
        createUserInfoTable(connection);
    }

    private void dropUserInfoTable(Connection connection) {
        String query = "DROP TABLE IF EXISTS user_information";
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            logger.info("Deleted table: user_information");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void dropUserTable(Connection connection) {
        String query = "DROP TABLE IF EXISTS user";
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            logger.info("Deleted table: user");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void dropUserGroupTable(Connection connection) {
        String query = "DROP TABLE IF EXISTS user_group";
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            logger.info("Deleted table: user_group");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void createUserGroupTable(Connection connection) {
        String query = "CREATE TABLE user_group (id INT(11) AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))";
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            logger.info("Created table: user_group");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void createUserTable(Connection connection) {
        String query = "CREATE TABLE user (" +
                "id INT(11) AUTO_INCREMENT PRIMARY KEY, username VARCHAR(40), " +
                "password VARCHAR(40), is_active TINYINT(1), user_group_id INT(11), age INT(11), " +
                "FOREIGN KEY (user_group_id) REFERENCES user_group(id));";
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            logger.info("Created table: user");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void createUserInfoTable(Connection connection) {
        String query = "CREATE TABLE user_information (" +
                "user_id INT(11) PRIMARY KEY, address VARCHAR(100), telephone VARCHAR(40), " +
                "FOREIGN KEY (user_id) REFERENCES user(id))";
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            logger.info("Created table: user_information");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}