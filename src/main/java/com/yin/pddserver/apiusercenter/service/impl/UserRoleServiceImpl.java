package com.yin.pddserver.apiusercenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yin.pddserver.apiusercenter.service.UserRoleService;
import com.yin.pddserver.common.api.user.mapper.UserRoleMapper;
import com.yin.pddserver.common.api.user.po.UserRolePo;
import com.yin.pddserver.common.base.service.impl.BaseWisdomServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl extends BaseWisdomServiceImpl<UserRoleMapper, UserRolePo> implements UserRoleService {

    public List<String> getUserRoleIdList(String userId) {
        List<UserRolePo> userRoleList = this.list(new QueryWrapper<UserRolePo>().lambda().eq(UserRolePo::getUserId, userId));
        return userRoleList.stream().map(UserRolePo::getRoleId).collect(Collectors.toList());
    }

}
