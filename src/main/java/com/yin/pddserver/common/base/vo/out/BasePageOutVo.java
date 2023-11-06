package com.yin.pddserver.common.base.vo.out;

import com.yin.pddserver.common.base.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BasePageOutVo<T> extends BaseVo {

    private List<T> records = new ArrayList();

    private Long total = 0L;

}
