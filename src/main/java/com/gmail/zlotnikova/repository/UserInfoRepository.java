package com.gmail.zlotnikova.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.zlotnikova.repository.model.UserInfo;

public interface UserInfoRepository extends GeneralRepository<UserInfo> {

    int deleteUserUnderLimitAge(Connection connection, int age) throws SQLException;

    List<UserInfo> findAll(Connection connection) throws SQLException;

}
