package com.yin.pddserver.apiusercenter.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yin.pddserver.apiusercenter.vo.in.UserInVo;
import com.yin.pddserver.apiusercenter.vo.in.UserPageInVo;
import com.yin.pddserver.common.api.user.po.UserPo;
import com.yin.pddserver.common.base.service.BaseWisdomService;

import java.io.Serializable;
import java.util.Collection;

public interface UserService extends BaseWisdomService<UserPo> {


    UserPo getById(String tnId, String id);

    boolean removeByIds(String tnId, Collection<? extends Serializable> idList);

    boolean remove(String tnId, Wrapper<UserPo> queryWrapper);

    UserPo saveOrUpdate(String tnId, UserPo userPo);

    boolean saveOrUpdateBatch(Collection<UserPo> entityList);

    /**
     * 根据VO创建或者更新
     *
     * @param vo
     * @return
     * @throws Exception
     */
    UserPo saveOrUpdate(String tnId, UserInVo vo) throws Exception;

    /**
     * 生成查询参数
     *
     * @param vo
     * @return
     */
    QueryWrapper<UserPo> createQueryWrapper(UserPageInVo vo);
}
