package com.yin.pddserver.common.utils.wechatexpress.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 发件人BO
 *
 * @author yin.weilong
 * @date 2019.08.31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatExpressSenderBo {

    /**
     * 发件人姓名，不超过64字节
     */
    @Length(max = 20, message = "发件人姓名最大长度20")
    @NotBlank(message = "发件人姓名不能为空")
    private String name;

    /**
     * 发件人座机号码，若不填写则必须填写 mobile，不超过32字节
     */
    @Length(max = 32, message = "发件人座机号码最大长度32")
    private String tel;

    /**
     * 发件人手机号码，若不填写则必须填写 tel，不超过32字节
     */
    @Length(max = 32, message = "发件人手机号码最大长度32")
    private String mobile;

    /**
     * 发件人省份，比如："广东省"，不超过64字节
     */
    @Length(max = 20, message = "发件人省份最大长度20")
    @NotBlank(message = "发件人省份不能为空")
    private String province;

    /**
     * 发件人市/地区，比如："广州市"，不超过64字节
     */
    @Length(max = 20, message = "发件人市/地区最大长度20")
    @NotBlank(message = "发件人市/地区不能为空")
    private String city;

    /**
     * 发件人区/县，比如："海珠区"，不超过64字节
     */
    @Length(max = 20, message = "发件人区/县最大长度20")
    @NotBlank(message = "发件人区/县不能为空")
    private String area;


    /**
     * 发件人详细地址，比如："XX路XX号XX大厦XX"，不超过512字节
     */
    @Length(max = 170, message = "发件人详细地址最大长度170")
    @NotBlank(message = "发件人详细地址不能为空")
    private String address;
}
