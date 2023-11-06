package com.yin.pddserver.common.utils.wechatexpress.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 收件人BO
 *
 * @author yin.weilong
 * @date 2019.08.31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatExpressReceiverBo {

    /**
     * 收件人姓名，不超过64字节
     */
    @Length(max = 20, message = "收件人姓名最大长度20")
    @NotBlank(message = "收件人姓名不能为空")
    private String name;

    /**
     * 收件人座机号码，若不填写则必须填写 mobile，不超过32字节
     */
    @Length(max = 32, message = "收件人座机号码最大长度32")
    private String tel;

    /**
     * 收件人手机号码，若不填写则必须填写 tel，不超过32字节
     */
    @Length(max = 32, message = "收件人手机号码最大长度32")
    private String mobile;

    /**
     * 收件人省份，比如："广东省"，不超过64字节
     */
    @Length(max = 20, message = "收件人省份最大长度20")
    @NotBlank(message = "收件人省份不能为空")
    private String province;

    /**
     * 收件人市/地区，比如："广州市"，不超过64字节
     */
    @Length(max = 20, message = "收件人市/地区最大长度20")
    @NotBlank(message = "收件人市/地区不能为空")
    private String city;

    /**
     * 收件人区/县，比如："海珠区"，不超过64字节
     */
    @Length(max = 20, message = "收件人区/县最大长度20")
    @NotBlank(message = "收件人区/县不能为空")
    private String area;


    /**
     * 收件人详细地址，比如："XX路XX号XX大厦XX"，不超过512字节
     */
    @Length(max = 170, message = "收件人详细地址最大长度170")
    @NotBlank(message = "收件人详细地址不能为空")
    private String address;
}
