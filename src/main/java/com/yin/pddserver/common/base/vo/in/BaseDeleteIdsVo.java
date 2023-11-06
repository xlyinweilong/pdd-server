package com.yin.pddserver.common.base.vo.in;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class BaseDeleteIdsVo {

    @NotNull
    @Size(min = 1)
    private List<String> ids;

}
