package com.yin.pddserver.common.api.user.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yin.pddserver.common.api.user.enums.PowerChannelTypeEnum;
import com.yin.pddserver.common.base.po.BaseDataPo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@TableName(value = "u_role_power_channel")
public class RolePowerChannelPo extends BaseDataPo {

    @TableField(value = "role_id")
    @NotBlank
    private String roleId;

    @TableField(value = "channel_id")
    @NotBlank
    private String channelId;

    @TableField(value = "channel_type")
    private String channelType;

    /**
     * read或者write
     */
    @TableField(value = "type")
    private String type = PowerChannelTypeEnum.READ.name();

    public RolePowerChannelPo(String roleId, String channelId, String type, String channelType) {
        this.roleId = roleId;
        this.channelId = channelId;
        this.type = type;
        this.channelType = channelType;
    }

}
