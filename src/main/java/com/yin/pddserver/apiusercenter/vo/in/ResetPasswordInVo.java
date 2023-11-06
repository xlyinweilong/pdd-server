package com.yin.pddserver.apiusercenter.vo.in;

import com.yin.pddserver.common.base.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ResetPasswordInVo extends BaseVo {

    @NotBlank
    @Length(max = 30)
    private String oldPasswd;

    @NotNull
    @Length(max = 30)
    private String newPasswd;


}
