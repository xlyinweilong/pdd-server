package com.yin.pddserver.common.utils.mp.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yin.weilong
 * @date 2019.09.13
 */
@XStreamAlias("xml")
@Data
public class InputMessage implements Serializable {

    /**
     * 基本信息
     */
    private static final long serialVersionUID = 1L;
    @XStreamAlias("ToUserName")
    private String toUserName;
    @XStreamAlias("FromUserName")
    private String fromUserName;
    @XStreamAlias("CreateTime")
    private Long createTime;
    @XStreamAlias("MsgType")
    private String msgType = "text";
    @XStreamAlias("MsgId")
    private Long msgId;
    /**
     * 文本消息
     */
    @XStreamAlias("Content")
    private String content;
    /**
     * 图片消息
     */
    @XStreamAlias("PicUrl")
    private String picUrl;
    /**
     * 位置消息
     */
    @XStreamAlias("LocationX")
    private String locationX;
    @XStreamAlias("LocationY")
    private String locationY;
    @XStreamAlias("Scale")
    private Long scale;
    @XStreamAlias("Label")
    private String label;
    /**
     * 链接消息
     */
    @XStreamAlias("Title")
    private String title;
    @XStreamAlias("Description")
    private String description;
    @XStreamAlias("Url")
    private String url;
    /**
     * 语音信息
     */
    @XStreamAlias("MediaId")
    private String mediaId;
    @XStreamAlias("Format")
    private String format;
    @XStreamAlias("Recognition")
    private String recognition;
    /**
     * 事件
     */
    @XStreamAlias("Event")
    private String event;
    @XStreamAlias("EventKey")
    private String eventKey;
    @XStreamAlias("Ticket")
    private String ticket;
    /**
     * 卡卷
     */
    @XStreamAlias("CardId")
    private String cardId;
    //拒绝原因
    @XStreamAlias("RefuseReason")
    private String refuseReason;
    //是否为转赠领取，1代表是，0代表否。
    @XStreamAlias("IsGiveByFriend")
    private Integer isGiveByFriend;
    //当IsGiveByFriend为1时填入的字段，表示发起转赠用户的openid
    @XStreamAlias("FriendUserName")
    private String friendUserName;
    //code序列号
    @XStreamAlias("UserCardCode")
    private String userCardCode;
    //为保证安全，微信会在转赠发生后变更该卡券的code号，该字段表示转赠前的code。
    @XStreamAlias("OldUserCardCode")
    private String oldUserCardCode;
    //领取场景值，用于领取渠道数据统计。可在生成二维码接口及添加Addcard接口中自定义该字段的字符串值。
    @XStreamAlias("OuterStr")
    private String outerStr;
    //用户删除会员卡后可重新找回，当用户本次操作为找回时，该值为1，否则为0
    @XStreamAlias("IsRestoreMemberCard")
    private Integer isRestoreMemberCard;
    //领券用户的UnionId
    @XStreamAlias("UnionId")
    private String unionId;
    //OuterIdOuterId
    @XStreamAlias("OuterId")
    private Integer outerId;
    @XStreamAlias("IsRecommendByFriend")
    private Integer isRecommendByFriend;
    @XStreamAlias("SourceScene")
    private String sourceScene;
    @XStreamAlias("MenuId")
    private String menuId;
    @XStreamAlias("Status")
    private String status;
    @XStreamAlias("MsgID")
    private String msgID;
    @XStreamAlias("ConsumeSource")
    private String consumeSource;
    @XStreamAlias("LocationName")
    private String locationName;




}
