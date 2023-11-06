package com.yin.pddserver.common.utils.mp.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @author yin.weilong
 * @date 2019.09.13
 */
@XStreamAlias("xml")
@Data
public class OutputMessage {

    @XStreamAlias("ToUserName")
    @XStreamCDATA
    private String toUserName;

    @XStreamAlias("FromUserName")
    @XStreamCDATA
    private String fromUserName;

    @XStreamAlias("CreateTime")
    private Long createTime;

    @XStreamAlias("MsgType")
    @XStreamCDATA
    private String msgType = "text";

    @XStreamAlias("Image")
    @XStreamCDATA
    private ImageMessage image;

    @XStreamAlias("Content")
    @XStreamCDATA
    private String content;

    @XStreamAlias("ArticleCount")
    @XStreamCDATA
    private Integer articleCount;

    @XStreamAlias("Articles")
    @XStreamCDATA
    private ArticlesMessage articles;



}
