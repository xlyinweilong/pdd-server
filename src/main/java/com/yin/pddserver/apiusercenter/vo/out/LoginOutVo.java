package com.yin.pddserver.apiusercenter.vo.out;

import com.yin.pddserver.common.base.vo.BaseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LoginOutVo extends BaseVo {

    private String name;

    private String token;

    private List<String> powers;

}
