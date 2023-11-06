package com.yin.pddserver.common.base.vo.in;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BaseDeleteIdVo {

    @NotBlank
    private String id;

}
