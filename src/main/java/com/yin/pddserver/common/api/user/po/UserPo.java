package com.yin.pddserver.common.api.user.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yin.pddserver.common.api.user.enums.UserTypeEnum;
import com.yin.pddserver.common.base.po.BasePo;
import com.yin.pddserver.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@TableName(value = "u_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPo extends BasePo {

    @TableField(value = "username")
    @Length(max = 50)
    private String username;

    @TableField(value = "password", updateStrategy = FieldStrategy.NOT_EMPTY)
    @Length(max = 30)
    private String password;

    @TableField(value = "name", updateStrategy = FieldStrategy.IGNORED)
    @Length(max = 50)
    private String name;

    /**
     * 入职时间
     */
    @TableField(value = "hired_date", updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date hiredDate;

    /**
     * 员工工号
     */
    @TableField(value = "jobnumber", updateStrategy = FieldStrategy.IGNORED)
    @Length(max = 50)
    private String jobnumber;


    /**
     * 手机号
     */
    @TableField(value = "mobile", updateStrategy = FieldStrategy.IGNORED)
    @Length(max = 30)
    private String mobile;

    @TableField(value = "is_super", updateStrategy = FieldStrategy.IGNORED)
    private Boolean isSuper = false;

    /**
     * 是否离职
     */
    @TableField(value = "is_leave", updateStrategy = FieldStrategy.IGNORED)
    private Boolean isLeave = false;

    /**
     * 离职时间
     */
    @TableField(value = "leave_date", updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaveDate;

    /**
     * 禁用
     */
    @TableField(value = "disabled", updateStrategy = FieldStrategy.IGNORED)
    private Boolean disabled = false;

    /**
     * 禁用原因
     */
    @TableField(value = "disable_reason", updateStrategy = FieldStrategy.IGNORED)
    @Length(max = 200)
    private String disableReason;

    /**
     * 类型
     */
    @TableField(value = "type", updateStrategy = FieldStrategy.IGNORED)
    @Length(max = 20)
    private String type = UserTypeEnum.COMMON.name();

    /**
     * 钉钉用户Id
     */
    @TableField(value = "dd_user_id", updateStrategy = FieldStrategy.IGNORED)
    @Length(max = 32)
    private String ddUserId;

    @TableField(value = "dd_userid", updateStrategy = FieldStrategy.IGNORED)
    private String ddUid;

    /**
     * 钉钉职位信息
     */
    @TableField(value = "dd_position", updateStrategy = FieldStrategy.IGNORED)
    private String ddPosition;

    /**
     * 钉钉职位信息
     */
    @TableField(value = "dd_work_place", updateStrategy = FieldStrategy.IGNORED)
    private String ddWorkPlace;

    /**
     * CRM员工获得评价
     */
    @TableField(value = "total_rate", updateStrategy = FieldStrategy.IGNORED)
    private Integer totalRate;

    /**
     * CRM员工获得评价
     */
    @TableField(value = "total_count", updateStrategy = FieldStrategy.IGNORED)
    private Integer totalCount;

    /**
     * 关联的企业微信用户ID
     */
    @TableField(value = "qywc_user_id", updateStrategy = FieldStrategy.IGNORED)
    @Length(max = 100)
    private String qywcUserId;

    /**
     * 关联的企业微信用户OPEN_ID
     */
    @TableField(value = "qywc_user_open_id", updateStrategy = FieldStrategy.IGNORED)
    @Length(max = 100)
    private String qywcUserOpenId;

    /**
     * 关联的企业微信三方用户OPEN_ID
     */
    @TableField(value = "qywc_third_party_open_user_id", updateStrategy = FieldStrategy.IGNORED)
    @Length(max = 100)
    private String qywcThirdPartyOpenUserId;

    /**
     * 关联的企业微信用户最后登录的设备ID
     */
    @TableField(value = "qywc_last_device_id", updateStrategy = FieldStrategy.IGNORED)
    @Length(max = 200)
    private String qywcLastDeviceId;

    private transient List<String> channelIds;

    public Integer getWorkingYears() {
        LocalDate now = LocalDate.now();
        if (hiredDate == null) {
            return null;
        }
        if (leaveDate != null) {
            return DateUtils.asLocalDate(leaveDate).getYear() - DateUtils.asLocalDate(hiredDate).getYear();
        }
        return now.getYear() - DateUtils.asLocalDate(hiredDate).getYear();
    }

}
