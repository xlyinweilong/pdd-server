package com.yin.pddserver.common.auth.service;

/**
 * ip限制服务
 */
public interface IpLimitService {


    /**
     * 获取次数
     *
     * @param ip
     * @return
     */
    Integer get(String ip);

    /**
     * 增加次数
     *
     * @param ip
     * @return
     */
    Integer addTimes(String ip);

}
