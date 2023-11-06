package com.yin.pddserver.common.utils.wechatexpress.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 商品信息，会展示到物流服务通知和电子面单中BO
 *
 * @author yin.weilong
 * @date 2019.08.31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatExpressShopBo {

    /**
     * 商家小程序的路径，建议为订单页面
     */
    @NotBlank(message = "商家小程序的路径必填")
    private String wxaPath;

    /**
     * 商品缩略图 url
     */
    @NotBlank(message = "商品缩略图必填")
    private String imgUrl;

    /**
     * 商品名称, 不超过128字节
     */
    @NotBlank(message = "商品名称必填")
    @Length(max = 128, message = "商品名称不超过128字节")
    private String goodsName;

    /**
     * 商品数量
     */
    @NotNull(message = "商品数量必填")
    private Integer goodsCount;


}
