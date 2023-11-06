package com.yin.pddserver.common.utils.wechatexpress.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 服务类型BO
 *
 * @author yin.weilong
 * @date 2019.08.31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatExpressOrderBo {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID必填")
    private String orderId;

    /**
     * 快递公司ID
     */
    @NotBlank(message = "快递公司ID必填")
    private String deliveryId;


    private String waybillId;


}
