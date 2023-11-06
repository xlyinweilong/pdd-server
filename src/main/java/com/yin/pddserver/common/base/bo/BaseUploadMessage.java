package com.yin.pddserver.common.base.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseUploadMessage implements Serializable {
    private Integer totalRowCount = 0;
    private Integer nowRowCount = -1;
    private String errorUrl;
    private String useTime;
    private Integer status = 0;
    private String message;
    private Object data;

    public BaseUploadMessage() {
    }

    public BaseUploadMessage(int status) {
        this.status = status;
    }

    public BaseUploadMessage(int status, String useTime) {
        this.status = status;
        this.useTime = useTime;
    }

    public BaseUploadMessage(int status, String useTime, String message) {
        this.status = status;
        this.useTime = useTime;
        this.message = message;
    }

    public BaseUploadMessage(String errorUrl, int status, String useTime) {
        this.errorUrl = errorUrl;
        this.status = status;
        this.useTime = useTime;
    }

    public BaseUploadMessage(int totalRowCount, int nowRowCount) {
        this.totalRowCount = totalRowCount;
        this.nowRowCount = nowRowCount;
    }

    public BaseUploadMessage(int status, String useTime, Object data) {
        this.status = status;
        this.useTime = useTime;
        this.data = data;
    }
}
