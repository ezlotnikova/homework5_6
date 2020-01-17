package com.gmail.zlotnikova.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionRepository {

    Connection getInitialConnection() throws SQLException;

    Connection getConnection() throws SQLException;

}