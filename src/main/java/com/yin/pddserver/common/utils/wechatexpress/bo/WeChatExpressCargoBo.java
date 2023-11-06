package com.yin.pddserver.common.utils.wechatexpress.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * 包裹信息，将传递给快递公司BO
 *
 * @author yin.weilong
 * @date 2019.08.31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatExpressCargoBo {

    /**
     * 包裹数量, 需要和detail_list size保持一致
     */
    @NotNull(message = "包裹数量必填")
    @Min(value = 1, message = "包裹数量必填")
    private Integer count;

    /**
     * 包裹总重量，单位是千克(kg)
     */
    @NotNull(message = "包裹总重量必填")
    private BigDecimal weight;

    /**
     * 包裹长度，单位厘米(cm)
     */
    @NotNull(message = "包裹长度必填")
    private BigDecimal spaceX;

    /**
     * 包裹宽度，单位厘米(cm)
     */
    @NotNull(message = "包裹宽度必填")
    private BigDecimal spaceY;

    /**
     * 包裹高度，单位厘米(cm)
     */
    @NotNull(message = "包裹高度必填")
    private BigDecimal spaceZ;

    /**
     * 包裹中商品详情列表
     */
    @NotNull(message = "包裹中商品详情列表必填")
    @Size(min = 1, message = "包裹中商品详情列表必填")
    private List<WeChatExpressCargoDetailBo> detailList;


}
