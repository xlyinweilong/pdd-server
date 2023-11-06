package com.yin.pddserver.apiusercenter.vo.in;

import com.yin.pddserver.common.base.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class RolePowerChannelInVo extends BaseVo {

    @NotNull
    private String roleId;

    private List<String> readList;

    private List<String> writeList;

}
