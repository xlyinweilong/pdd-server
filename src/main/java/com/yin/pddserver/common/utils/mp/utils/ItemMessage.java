package com.yin.pddserver.common.utils.mp.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @author yin.weilong
 * @date 2019.09.13
 */
@XStreamAlias("item")
@Data
public class ItemMessage {
    @XStreamAlias("Title")
    @XStreamCDATA
    private String title;

    @XStreamAlias("Description")
    @XStreamCDATA
    private String description;

    @XStreamAlias("PicUrl")
    @XStreamCDATA
    private String picUrl;

    @XStreamAlias("Url")
    @XStreamCDATA
    private String url;
}
