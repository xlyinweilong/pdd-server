package com.yin.pddserver.common.utils.mp.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @author yin.weilong
 * @date 2019.09.13
 */
@Data
public class MediaIdMessage {
    @XStreamAlias("MediaId")
    @XStreamCDATA
    private String MediaId;
}
