package com.yin.pddserver.common.enums;

public enum CommonInputTypeEnum {

    NORMAL, IMPORT;

    public String getMean() {
        switch (this) {
            case NORMAL:
                return "正常";
            case IMPORT:
                return "导入";
            default:
                return null;
        }
    }
}
