package com.gmail.zlotnikova.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gmail.zlotnikova.repository.ConnectionRepository;
import com.gmail.zlotnikova.repository.UserInfoRepository;
import com.gmail.zlotnikova.repository.impl.ConnectionRepositoryImpl;
import com.gmail.zlotnikova.repository.impl.UserInfoRepositoryImpl;
import com.gmail.zlotnikova.repository.model.User;
import com.gmail.zlotnikova.repository.model.UserInfo;
import com.gmail.zlotnikova.service.UserInfoService;
import com.gmail.zlotnikova.service.model.UserDTO;
import com.gmail.zlotnikova.service.model.UserInfoDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserInfoServiceImpl implements UserInfoService {

    private static UserInfoService instance;

    private UserInfoServiceImpl() {
    }

    public static UserInfoService getInstance() {
        if (instance == null) {
            instance = new UserInfoServiceImpl();
        }
        return instance;
    }

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    UserInfoRepository userInfoRepository = UserInfoRepositoryImpl.getInstance();

    @Override
    public UserInfoDTO add(UserInfoDTO userInfoDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                UserInfo userInfo = convertDTOToUserInfo(userInfoDTO);
                userInfo = userInfoRepository.add(connection, userInfo);
                userInfoDTO = convertUserInfoToDTO(userInfo);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return userInfoDTO;
    }

    @Override
    public List<UserInfoDTO> findAll() {
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<UserInfo> userInfoList = userInfoRepository.findAll(connection);
                for (int i = 0; i < userInfoList.size(); i++) {
                    UserInfoDTO userInfoDTO = convertUserInfoToDTO(userInfoList.get(i));
                    userInfoDTOList.add(userInfoDTO);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return userInfoDTOList;
    }

    private UserInfoDTO convertUserInfoToDTO(UserInfo userInfo) {
        return  UserInfoDTO.newBuilder()
                .userId(userInfo.getUserId())
                .address(userInfo.getAddress())
                .telephone(userInfo.getTelephone())
                .build();
    }

    private UserInfo convertDTOToUserInfo(UserInfoDTO userInfoDTO) {
        return UserInfo.newBuilder()
                .userId(userInfoDTO.getUserId())
                .address(userInfoDTO.getAddress())
                .telephone(userInfoDTO.getTelephone())
                .build();
    }

}
