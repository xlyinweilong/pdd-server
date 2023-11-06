package com.yin.pddserver.common.api.user.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yin.pddserver.common.base.po.BasePo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@TableName(value = "u_role")
public class RolePo extends BasePo {

    @TableField(value = "name")
    @NotBlank
    @Length(max = 100)
    private String name;

}
