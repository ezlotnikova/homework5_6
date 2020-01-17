package com.gmail.zlotnikova.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.zlotnikova.repository.model.UserGroup;
import com.gmail.zlotnikova.service.model.UserGroupDTO;

public interface UserGroupRepository extends GeneralRepository<UserGroup> {

    UserGroupDTO showGroupSize(Connection connection, int groupId) throws SQLException;

}