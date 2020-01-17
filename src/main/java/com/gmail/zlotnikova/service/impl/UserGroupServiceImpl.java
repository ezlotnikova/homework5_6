package com.gmail.zlotnikova.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.zlotnikova.repository.ConnectionRepository;
import com.gmail.zlotnikova.repository.UserGroupRepository;
import com.gmail.zlotnikova.repository.impl.ConnectionRepositoryImpl;
import com.gmail.zlotnikova.repository.impl.UserGroupRepositoryImpl;
import com.gmail.zlotnikova.repository.model.UserGroup;
import com.gmail.zlotnikova.service.UserGroupService;
import com.gmail.zlotnikova.service.model.UserGroupDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserGroupServiceImpl implements UserGroupService {

    private static UserGroupService instance;

    private UserGroupServiceImpl() {
    }

    public static UserGroupService getInstance() {
        if (instance == null) {
            instance = new UserGroupServiceImpl();
        }
        return instance;
    }

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    UserGroupRepository userGroupRepository = UserGroupRepositoryImpl.getInstance();

    @Override
    public UserGroupDTO add(UserGroupDTO userGroupDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                UserGroup userGroup = convertDTOToUserGroup(userGroupDTO);
                userGroup = userGroupRepository.add(connection, userGroup);
                userGroupDTO = convertUserGroupToDTO(userGroup);
                connection.commit();
                return userGroupDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private UserGroupDTO convertUserGroupToDTO(UserGroup userGroup) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setName(userGroup.getName());
        return userGroupDTO;
    }

    private UserGroup convertDTOToUserGroup(UserGroupDTO addUserGroupDTO) {
        UserGroup userGroup = UserGroup.newBuilder()
                .name(addUserGroupDTO.getName())
                .build();
        return userGroup;
    }

    @Override
    public UserGroupDTO showGroupSize(int groupId) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userGroupDTO = userGroupRepository.showGroupSize(connection, groupId);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return userGroupDTO;
    }

}