package com.yin.pddserver.common.api.user.po;

import com.baomidou.mybatisplus.annotation.*;
import com.yin.pddserver.common.utils.GenerateUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * 此表属于全局spms库
 */
@Getter
@Setter
@TableName(value = "u_power")
public class PowerPo {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id = GenerateUtil.createUUID();

    @TableField(value = "power_key")
    @NotBlank
    private String powerKey;

    @TableField(value = "power_name")
    @NotBlank
    private String powerName;

    @TableField(value = "pid", updateStrategy = FieldStrategy.IGNORED)
    private String pid;

    @TableField(value = "index_order")
    private Integer indexOrder;

    @TableField(value = "platform", updateStrategy = FieldStrategy.IGNORED)
    private String platform;

}
