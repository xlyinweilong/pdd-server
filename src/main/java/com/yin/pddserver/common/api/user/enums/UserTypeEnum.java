package com.yin.pddserver.common.api.user.enums;

public enum UserTypeEnum {

    COMMON,
    DING_DING,
    QYWC;


    public String getMean() {
        switch (this) {
            case COMMON:
                return "普通用户";
            case DING_DING:
                return "钉钉用户";
            case QYWC:
                return "企业微信用户";
            default:
                return null;
        }
    }
}
