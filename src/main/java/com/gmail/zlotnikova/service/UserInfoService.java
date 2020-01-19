package com.gmail.zlotnikova.service;

import java.util.List;

import com.gmail.zlotnikova.service.model.UserInfoDTO;

public interface UserInfoService extends GeneralService<UserInfoDTO> {

    List<UserInfoDTO> findAll();

}
