package com.yin.pddserver.apiusercenter.service;


import com.yin.pddserver.common.api.user.po.UserRolePo;
import com.yin.pddserver.common.base.service.BaseWisdomService;

import java.util.List;

public interface UserRoleService extends BaseWisdomService<UserRolePo> {

    List<String> getUserRoleIdList(String userId);
}
