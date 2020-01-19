package com.gmail.zlotnikova.repository;

import java.sql.Connection;

public interface DatabaseRepository {

    void createDatabase(Connection connection);

    void prepareTables(Connection connection);

}