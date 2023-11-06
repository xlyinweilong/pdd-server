package com.yin.pddserver.apiusercenter.service;


import com.yin.pddserver.common.api.user.po.PowerPo;
import com.yin.pddserver.common.base.service.BaseWisdomService;

import java.util.List;

public interface PowerService extends BaseWisdomService<PowerPo> {

    /**
     * 获取全部权限
     *
     * @return
     */
    List<PowerPo> getAll();

    /**
     * 加载自定义用户的权限
     *
     * @param userId
     * @param isSuper
     * @return
     */
    List<String> loadUserPowerKey(String userId, Boolean isSuper);


    /**
     * 删除缓存
     *
     * @param userId
     */
    void resetCache(String userId);

    /**
     * 删除缓存
     */
    void resetAllCache();


    /**
     * 安装权限
     */
    void setup();
}
