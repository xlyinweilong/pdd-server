package com.yin.pddserver.common.api.user.mapper;


import com.yin.pddserver.common.api.user.po.UserPo;
import com.yin.pddserver.common.base.mapper.BaseWisdomMapper;

import javax.annotation.Resource;

@Resource
public interface UserMapper extends BaseWisdomMapper<UserPo> {

//    @Cacheable(key = "methodName+':'+#id")
    UserPo selectById(String id);

//    @CacheEvict(key = "methodName+':'+#id")
    void saveOrUpdate(UserPo po);
}
