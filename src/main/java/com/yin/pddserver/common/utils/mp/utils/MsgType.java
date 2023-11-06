package com.yin.pddserver.common.utils.mp.utils;

/**
 * @author yin.weilong
 * @date 2019.09.13
 */
public enum  MsgType {
    Event("event"),
    Text("text"),
    Image("image"),
    Music("music"),
    Video("video"),
    Voice("voice"),
    Location("location"),
    Link("link");
    private String msgType = "";

    MsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * @return the msgType
     */
    @Override
    public String toString() {
        return msgType;
    }
}
