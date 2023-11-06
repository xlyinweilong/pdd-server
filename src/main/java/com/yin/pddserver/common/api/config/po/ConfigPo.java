package com.yin.pddserver.common.api.config.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yin.pddserver.common.base.po.BasePo;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Getter
@Setter
@TableName(value = "c_config")
public class ConfigPo extends BasePo {

    @TableField(value = "c_key")
    private String configKey;

    @TableField(value = "c_value", updateStrategy = FieldStrategy.IGNORED)
    private String configValue;

    @TableField(value = "c_describe")
    private String configDescribe;

    @TableField(value = "group_id")
    private String groupId;

    @TableField(value = "type")
    private String type;

    @TableField(value = "select_values")
    private String selectValues;

    @TableField(value = "select_values_name")
    private String selectValuesName;

    public List getSelectValueList() {
        if (StringUtils.isBlank(selectValues)) {
            return new ArrayList();
        }
        AtomicInteger index = new AtomicInteger(0);
        return Arrays.stream(selectValues.split(",")).map(s -> {
            Map map = new HashMap();
            map.put("value", s);
            map.put("name", selectValuesName.split(",")[index.getAndIncrement()]);
            return map;
        }).collect(Collectors.toList());
    }

    public void setSelectValueList(List list) {
    }
}
