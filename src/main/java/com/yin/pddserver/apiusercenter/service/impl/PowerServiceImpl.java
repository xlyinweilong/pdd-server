package com.yin.pddserver.apiusercenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yin.pddserver.apiusercenter.service.PowerService;
import com.yin.pddserver.apiusercenter.service.RolePowerService;
import com.yin.pddserver.apiusercenter.service.UserRoleService;
import com.yin.pddserver.common.api.user.mapper.PowerMapper;
import com.yin.pddserver.common.api.user.po.PowerPo;
import com.yin.pddserver.common.api.user.po.RolePowerPo;
import com.yin.pddserver.common.base.service.impl.BaseWisdomServiceImpl;
import com.yin.pddserver.common.cache.PermanentCacheService;
import com.yin.pddserver.common.utils.ClazzUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "PowerService")
public class PowerServiceImpl extends BaseWisdomServiceImpl<PowerMapper, PowerPo> implements PowerService {

    @Autowired
    private RolePowerService rolePowerService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PermanentCacheService permanentCacheService;

    public List<PowerPo> getAll() {
        return permanentCacheService.getList(PowerPo.class);
    }

    @Cacheable(key = "methodName+':'+#userId")
    public List<String> loadUserPowerKey(String userId, Boolean isSuper) {
        if (isSuper) {
            List<PowerPo> list = this.getAll();
            return list.stream().map(PowerPo::getPowerKey).collect(Collectors.toList());
        } else {
            List<String> roleIds = userRoleService.getUserRoleIdList(userId);
            if (roleIds.isEmpty()) {
                return new ArrayList<>();
            }
            List<RolePowerPo> rolePowerList = rolePowerService.list(new QueryWrapper<RolePowerPo>().lambda().in(RolePowerPo::getRoleId, roleIds));
            return rolePowerList.stream().map(RolePowerPo::getPowerKey).distinct().collect(Collectors.toList());
        }
    }

    @CacheEvict(key = "'loadUserPowerKey:'+#userId")
    public void resetCache(String userId) {
    }

    @CacheEvict(allEntries = true)
    public void resetAllCache() {
    }

    public void setup() {
        List<PowerPo> powerList = this.list(new QueryWrapper<PowerPo>().orderByAsc("index_order"));
        permanentCacheService.setList(ClazzUtils.getClassName(PowerPo.class), powerList);
    }


}
