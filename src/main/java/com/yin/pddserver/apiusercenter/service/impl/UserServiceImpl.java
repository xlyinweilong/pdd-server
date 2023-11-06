package com.yin.pddserver.apiusercenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yin.pddserver.apiusercenter.service.PowerService;
import com.yin.pddserver.apiusercenter.service.UserService;
import com.yin.pddserver.apiusercenter.vo.in.UserInVo;
import com.yin.pddserver.apiusercenter.vo.in.UserPageInVo;
import com.yin.pddserver.common.api.user.mapper.UserMapper;
import com.yin.pddserver.common.api.user.po.UserPo;
import com.yin.pddserver.common.base.service.impl.BaseWisdomServiceImpl;
import com.yin.pddserver.common.exceptions.MessageException;
import com.yin.pddserver.common.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

@Service
@CacheConfig(cacheNames = "UserService")
public class UserServiceImpl extends BaseWisdomServiceImpl<UserMapper, UserPo> implements UserService {

    @Autowired
    private PowerService powerService;

    @Override
    @Cacheable(key = "#tnId + ':' + #id")
    public UserPo getById(String tnId, String id) {
        return super.getById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean saveOrUpdateBatch(Collection<UserPo> entityList) {
        powerService.resetAllCache();
        return this.saveOrUpdateBatch(entityList, 1000);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean removeByIds(String tnId, Collection<? extends Serializable> idList) {
        powerService.resetAllCache();
        return super.removeByIds(idList);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean remove(String tnId, Wrapper<UserPo> queryWrapper) {
        return super.remove(queryWrapper);
    }

    @Override
    @CacheEvict(key = "#tnId + ':' + #userPo.id")
    public UserPo saveOrUpdate(String tnId, UserPo userPo) {
        super.saveOrUpdate(userPo);
        powerService.resetCache(userPo.getId());
        return userPo;
    }

    @Override
    @CacheEvict(key = "#tnId + ':' + #vo.id")
    public UserPo saveOrUpdate(String tnId, UserInVo vo) throws Exception {
        UserPo po = new UserPo();
        if (StringUtils.isNotBlank(vo.getId())) {
            po = this.getById(vo.getId());
            if (po == null) {
                throw new MessageException("数据已经不存在");
            }
        }
        BeanUtils.copyProperties(vo, po, "id");
        if (!vo.getIsLeave()) {
            po.setLeaveDate(null);
        }
        if (!vo.getDisabled()) {
            po.setDisableReason(null);
        }
        this.saveOrUpdate(po);
        powerService.resetCache(po.getId());
        return po;
    }

    public QueryWrapper<UserPo> createQueryWrapper(UserPageInVo vo) {
        QueryWrapper<UserPo> qw = new QueryWrapper<>();
        SqlUtils.notBlankLike(qw, "username", vo.getUsername());
        SqlUtils.notBlankLike(qw, "name", vo.getName());
        SqlUtils.notBlankLike(qw, "mobile", vo.getMobile());
        SqlUtils.notBlankLike(qw, "jobnumber", vo.getJobnumber());
        SqlUtils.notNullGe(qw, "hired_date", vo.getStartDate());
        SqlUtils.notNullLe(qw, "hired_date", vo.getEndDate());
        SqlUtils.notNullEq(qw, "Is_super", vo.getIsSuper());
        SqlUtils.notNullEq(qw, "is_leave", vo.getIsLeave());
        SqlUtils.notNullEq(qw, "disabled", vo.getDisabled());
        if (vo.getIsBindQywc() != null) {
            if (vo.getIsBindQywc()) {
                qw.lambda().isNotNull(UserPo::getQywcUserId);
            } else {
                qw.lambda().isNull(UserPo::getQywcUserId);
            }
        }
        qw.lambda().orderByDesc(UserPo::getCreateDate);
        return qw;
    }
}
