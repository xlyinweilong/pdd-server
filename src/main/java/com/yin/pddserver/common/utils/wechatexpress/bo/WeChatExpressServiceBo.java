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
public class WeChatExpressServiceBo {

    /**
     * 服务类型ID，详见已经支持的快递公司基本信息
     */
    @NotNull(message = "服务类型ID必填")
    private Integer serviceType;

    /**
     * 服务名称，详见已经支持的快递公司基本信息
     */
    @NotBlank(message = "服务名称必填")
    private String serviceName;

}
