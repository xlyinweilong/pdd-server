package com.yin.pddserver.apiusercenter.vo.in;

import com.yin.pddserver.common.base.vo.in.BasePageVo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class UserPageInVo extends BasePageVo {

    private String mobile;

    private String username;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String jobnumber;

    private Boolean isSuper;

    private Boolean isLeave;

    private Boolean disabled;

    private Boolean bindCrmEmploy;

    private Boolean findChannel;

    private Boolean isBindQywc;

    private Boolean activeInfo;

}
