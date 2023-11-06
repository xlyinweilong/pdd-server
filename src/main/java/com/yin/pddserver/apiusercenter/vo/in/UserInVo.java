package com.yin.pddserver.apiusercenter.vo.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yin.pddserver.common.base.vo.in.BaseDataVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class UserInVo extends BaseDataVo {

    @Length(max = 50)
    private String username;

    @Length(max = 30)
    private String password;

    @Length(max = 100)
    private String name;

    /**
     * 入职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull
    private Date hiredDate;

    /**
     * 员工工号
     */
    @NotBlank
    @Length(max = 50)
    private String jobnumber;


    /**
     * 手机号
     */
    @Length(max = 30)
    private String mobile;

    @NotNull
    private Boolean isSuper = false;

    /**
     * 是否离职
     */
    @NotNull
    private Boolean isLeave = false;

    /**
     * 离职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaveDate;

    /**
     * 禁用
     */
    @NotNull
    private Boolean disabled = false;

    /**
     * 禁用原因
     */
    @Length(max = 200)
    private String disableReason;

    private String qywcUserId;

}
