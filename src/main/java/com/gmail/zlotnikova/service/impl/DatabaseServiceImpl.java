package com.gmail.zlotnikova.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.zlotnikova.repository.ConnectionRepository;
import com.gmail.zlotnikova.repository.DatabaseRepository;
import com.gmail.zlotnikova.repository.impl.ConnectionRepositoryImpl;
import com.gmail.zlotnikova.repository.impl.DatabaseRepositoryImpl;
import com.gmail.zlotnikova.service.DatabaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseServiceImpl implements DatabaseService {

    private static DatabaseService instance;

    private DatabaseServiceImpl() {
    }

    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseServiceImpl();
        }
        return instance;
    }

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    DatabaseRepository databaseRepository = DatabaseRepositoryImpl.getInstance();

    @Override

    public void createDatabase() {
        try (Connection connection = connectionRepository.getInitialConnection()) {
            connection.setAutoCommit(false);
            try {
                databaseRepository.createDatabase(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void prepareTables() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                databaseRepository.prepareTables(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
