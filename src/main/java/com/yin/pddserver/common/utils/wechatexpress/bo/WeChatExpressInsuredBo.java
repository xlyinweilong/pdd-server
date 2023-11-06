package com.yin.pddserver.common.utils.wechatexpress.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 保价信息BO
 *
 * @author yin.weilong
 * @date 2019.08.31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatExpressInsuredBo {

    /**
     * 是否保价，0 表示不保价，1 表示保价
     */
    @NotNull(message = "是否保价必填")
    private Integer useInsured;

    /**
     * 保价金额，单位是分，比如: 10000 表示 100 元
     */
    private Integer insuredValue = 0;


}
