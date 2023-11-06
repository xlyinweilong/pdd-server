package com.yin.pddserver.common.api.user.enums;

public enum PowerChannelTypeEnum {

    READ,
    WRITE;


    public String getMean() {
        switch (this) {
            case READ:
                return "查询";
            case WRITE:
                return "操作";
            default:
                return null;
        }
    }
}
