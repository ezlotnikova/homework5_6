package com.gmail.zlotnikova.controller.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Random;

import com.gmail.zlotnikova.controller.HomeWorkController;
import com.gmail.zlotnikova.repository.constant.UserGroupEnum;
import com.gmail.zlotnikova.repository.model.User;
import com.gmail.zlotnikova.service.DatabaseService;
import com.gmail.zlotnikova.service.NewUserService;
import com.gmail.zlotnikova.service.UserGroupService;
import com.gmail.zlotnikova.service.UserService;
import com.gmail.zlotnikova.service.impl.DatabaseServiceImpl;
import com.gmail.zlotnikova.service.impl.NewUserServiceImpl;
import com.gmail.zlotnikova.service.impl.UserGroupServiceImpl;
import com.gmail.zlotnikova.service.impl.UserServiceImpl;
import com.gmail.zlotnikova.service.model.NewUserDTO;
import com.gmail.zlotnikova.service.model.UserDTO;
import com.gmail.zlotnikova.service.model.UserGroupDTO;
import com.gmail.zlotnikova.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomeWorkControllerImpl implements HomeWorkController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void runTaskOne() {

        DatabaseService databaseService = DatabaseServiceImpl.getInstance();
        NewUserService newUserService = NewUserServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        UserGroupService userGroupService = UserGroupServiceImpl.getInstance();

        databaseService.createDatabase();
        databaseService.prepareTables();

        //populate table user_group
        int userGroupAmount = UserGroupEnum.values().length;
        for (int i = 0; i < userGroupAmount; i++) {
            UserGroupDTO userGroupDTO = new UserGroupDTO();
            userGroupDTO.setName(String.valueOf(UserGroupEnum.values()[i]));
            userGroupService.add(userGroupDTO);
        }

        // populate table user
        int minUserAge = 25;
        int maxUserAge = 35;
        int userAmount = 30;
        for (int i = 0; i < userAmount; i++) {
            NewUserDTO newUserDTO = createNewUserDTO(i, minUserAge, maxUserAge);
            newUserService.add(newUserDTO);
        }

        //print all users
        List<User> users = userService.findAll();
        users.forEach(logger::info);

        //show random group
        int groupId = RandomUtil.getElement(1, userGroupAmount);
        UserGroupDTO userGroupDTO = userGroupService.showGroupSize(groupId);
        logger.info(userGroupDTO.toString());

        //delete user by age
        int ageLimit = 27;
        int usersDeleted = userService.deleteUserUnderLimitAge(ageLimit);
        logger.info(usersDeleted + " user(s) deleted under age " + ageLimit);

        //change status by age
        int lowerAgeLimit = 30;
        int upperAgeLimit = 33;
        int rowsUpdated = userService.changeStatusByAge(lowerAgeLimit, upperAgeLimit);
        logger.info("for " + rowsUpdated + " user(s) in age between " + lowerAgeLimit + " and " + upperAgeLimit +
                " status 'is active' changed from 'true' to 'false'");
    }

    private NewUserDTO createNewUserDTO(int index, int minUserAge, int maxUserAge) {
        Random random = new Random();
        int userGroupAmount = UserGroupEnum.values().length;
        NewUserDTO newUserDTO = NewUserDTO.newBuilder()
                .username("username_" + (index + 1))
                .password("password_" + (index + 1))
                .isActive(random.nextBoolean())
                .userGroupId(RandomUtil.getElement(1, userGroupAmount))
                .age(RandomUtil.getElement(minUserAge, maxUserAge))
                .address("address_" + (index + 1))
                .telephone("telephone_" + (index + 1))
                .build();
        return newUserDTO;
    }

}