package com.yin.pddserver.common.base.vo.in;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseSearchPageVo extends BasePageVo {

    private String searchKey;

    private List<String> type;

}
