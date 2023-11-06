package com.yin.pddserver.common.base.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.yin.pddserver.common.utils.GenerateUtil;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class BaseDataPo extends BaseTnPo {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id = GenerateUtil.createUUID();

    @Version
    private Long version = 0L;
}
