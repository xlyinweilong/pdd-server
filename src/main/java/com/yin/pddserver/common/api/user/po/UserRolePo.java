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
@TableName(value = "u_user_role")
public class UserRolePo extends BaseDataPo {

    @TableField(value = "user_id")
    @NotBlank
    private String userId;

    @TableField(value = "role_id")
    @NotBlank
    private String roleId;

    public UserRolePo(String roleId, String userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

}
