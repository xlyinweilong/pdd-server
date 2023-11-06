package com.yin.pddserver.common.base.vo.in;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yin.pddserver.common.utils.StrUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePageVo {

    private Integer pageIndex;

    private Integer pageSize;

    private String prop;

    private String order;

    public Page getPage() {
        return (pageIndex == null || pageSize == null) ? null : new Page(pageIndex, pageSize);
    }

    public boolean isAsc() {
        return "ascending".equals(order);
    }

    public String getField() {
        return StrUtils.capitalToInitial(prop);
    }

}
