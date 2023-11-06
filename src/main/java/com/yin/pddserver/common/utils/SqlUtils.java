package com.yin.pddserver.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yin.pddserver.common.base.vo.in.BasePageVo;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class SqlUtils {

    /**
     * 等于
     *
     * @param qw
     * @param column
     * @param val
     */
    public static void notBlankEq(QueryWrapper qw, String column, String val) {
        if (StringUtils.isNotBlank(val)) {
            qw.eq(column, val);
        }
    }

    /**
     * 登录，null的时候判断isNull
     * @param qw
     * @param column
     * @param val
     */
    public static void eqOrIsNull(QueryWrapper qw, String column, String val) {
        if (StringUtils.isNotBlank(val)) {
            qw.eq(column, val);
        } else {
            qw.isNull(column);
        }
    }

    /**
     * like
     *
     * @param qw
     * @param column
     * @param val
     */
    public static void notBlankLike(QueryWrapper qw, String column, String val) {
        if (StringUtils.isNotBlank(val)) {
            qw.like(column, val);
        }
    }

    /**
     * eq or like
     *
     * @param qw
     * @param column
     * @param val
     * @param isLike
     */
    public static void notBlankEqOrLike(QueryWrapper qw, String column, String val, Boolean isLike) {
        if (isLike == null || isLike) {
            notBlankLike(qw, column, val);
        } else {
            notBlankEq(qw, column, val);
        }
    }

    /**
     * 等于
     *
     * @param qw
     * @param column
     * @param val
     */
    public static void notNullEq(QueryWrapper qw, String column, Object val) {
        if (val != null) {
            qw.eq(column, val);
        }
    }

    /**
     * 不等于
     *
     * @param qw
     * @param column
     * @param val
     */
    public static void notBlankNe(QueryWrapper qw, String column, String val) {
        if (StringUtils.isNotBlank(val)) {
            qw.ne(column, val);
        }
    }

    /**
     * 大于等于
     *
     * @param qw
     * @param column
     * @param val
     */
    public static void notNullGe(QueryWrapper qw, String column, Date val) {
        if (val != null) {
            qw.ge(column, val);
        }
    }

    public static void notNullGe(QueryWrapper qw, String column, String val) {
        if (val != null) {
            qw.ge(column, val);
        }
    }

    public static void notNullGe(QueryWrapper qw, String column, LocalDate val) {
        if (val != null) {
            qw.ge(column, val);
        }
    }

    public static void notNullGe(QueryWrapper qw, String column, LocalDateTime val) {
        if (val != null) {
            qw.ge(column, val);
        }
    }

    public static void notNullGe(QueryWrapper qw, String column, BigDecimal val) {
        if (val != null) {
            qw.ge(column, val);
        }
    }

    public static void notNullGt(QueryWrapper qw, String column, BigDecimal val) {
        if (val != null) {
            qw.gt(column, val);
        }
    }


    /**
     * 小于等于
     *
     * @param qw
     * @param column
     * @param val
     */
    public static void notNullLe(QueryWrapper qw, String column, Date val) {
        if (val != null) {
            qw.le(column, val);
        }
    }

    public static void notNullLe(QueryWrapper qw, String column, String val) {
        if (val != null) {
            qw.le(column, val);
        }
    }

    public static void notNullLe(QueryWrapper qw, String column, LocalDate val) {
        if (val != null) {
            qw.le(column, val);
        }
    }

    public static void notNullLe(QueryWrapper qw, String column, BigDecimal val) {
        if (val != null) {
            qw.le(column, val);
        }
    }

    public static void notNullLt(QueryWrapper qw, String column, BigDecimal val) {
        if (val != null) {
            qw.lt(column, val);
        }
    }


    /**
     * 包含
     *
     * @param qw
     * @param column
     * @param val
     */
    public static void notNullIn(QueryWrapper qw, String column, List<?> val) {
        if (val != null) {
            qw.in(column, val);
        }
    }

    /**
     * 包含
     *
     * @param qw
     * @param column
     * @param val
     */
    public static void notEmptyIn(QueryWrapper qw, String column, List val) {
        if (val != null && val.size() > 0) {
            if (val.size() == 1) {
                qw.eq(column, val.get(0));
            } else {
                qw.in(column, val);
            }
        }
    }

    public static void notEmptyBetweenMonth(QueryWrapper qw, String column, List<String> monthList) {
        if (monthList != null && monthList.size() == 2) {
            qw.between(column, Integer.parseInt(monthList.get(0).replace("-", "")), Integer.parseInt(monthList.get(1).replace("-", "")));
        }
    }

    /**
     * 排序
     *
     * @param qw
     * @param vo
     */
    public static void orderBy(QueryWrapper qw, BasePageVo vo) {
        if (StringUtils.isNotBlank(vo.getField()) && StringUtils.isNotBlank(vo.getOrder())) {
            qw.orderBy(true, vo.isAsc(), vo.getField());
        }
    }
}
