package com.yin.pddserver.common.api.user.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yin.pddserver.common.base.po.BaseDataPo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@TableName(value = "u_role_power")
public class RolePowerPo extends BaseDataPo {

    @TableField(value = "role_id")
    @NotBlank
    private String roleId;

    @TableField(value = "power_key")
    @NotBlank
    private String powerKey;

    public RolePowerPo(String roleId, String powerKey) {
        this.roleId = roleId;
        this.powerKey = powerKey;
    }


}
