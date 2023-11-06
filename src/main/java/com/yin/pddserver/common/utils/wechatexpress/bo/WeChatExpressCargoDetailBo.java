package com.yin.pddserver.common.utils.wechatexpress.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 包裹明细信息，将传递给快递公司BO
 *
 * @author yin.weilong
 * @date 2019.08.31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatExpressCargoDetailBo {

    /**
     * 商品名，不超过128字节
     */
    @NotBlank(message = "商品名必填")
    @Length(max = 128, message = "商品名长度不超过128")
    private String name;

    /**
     * 商品数量
     */
    @NotNull(message = "商品数量必填")
    @Min(value = 1, message = "商品数量必填")
    private Integer count;


}
