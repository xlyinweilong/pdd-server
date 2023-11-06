package com.yin.pddserver.common.config.druid;

import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatLoggerAdapter;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatLogger extends DruidDataSourceStatLoggerAdapter implements DruidDataSourceStatLogger {

    @Override
    public void log(DruidDataSourceStatValue statValue) {
        log.info(JSONObject.toJSONString(statValue));
    }

}
