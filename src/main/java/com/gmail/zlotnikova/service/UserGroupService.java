package com.gmail.zlotnikova.service;

import com.gmail.zlotnikova.service.model.UserGroupDTO;

public interface UserGroupService extends GeneralService<UserGroupDTO> {

    UserGroupDTO showGroupSize(int groupId);

}
