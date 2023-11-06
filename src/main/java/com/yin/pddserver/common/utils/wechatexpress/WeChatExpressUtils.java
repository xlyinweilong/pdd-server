package com.yin.pddserver.common.utils.wechatexpress;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yin.pddserver.common.http.HttpSendUtils;
import com.yin.pddserver.common.utils.wechatexpress.bo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 微信物流助手接口
 *
 * @author yin.weilong
 * @date 2019.08.31
 */
@Slf4j
public class WeChatExpressUtils {


    /**
     * 绑定、解绑物流账号
     *
     * @param token
     * @param type           bind表示绑定，unbind表示解除绑定
     * @param biz_id         快递公司客户编码
     * @param delivery_id    快递公司ID
     * @param password       快递公司客户密码
     * @param remark_content 备注内容（提交EMS审核需要）
     * @return
     * @throws Exception
     */
    public static JSONObject bindAccount(String token, String type, String biz_id, String delivery_id, String password, String remark_content) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("biz_id", biz_id);
        jsonObject.put("delivery_id", delivery_id);
        jsonObject.put("password", password);
        jsonObject.put("remark_content", remark_content);
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/account/bind?access_token=";
        return WeChatUtils.callApi(url, token, jsonObject);

    }


    /**
     * 获取所有绑定的物流账号
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static JSONObject getAllAccount(String token) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/account/getall?access_token=";
        String str = HttpSendUtils.sendPostJson(url + token, jsonObject);
        JSONObject rs = JSONObject.parseObject(str);
        return rs;
    }

    /**
     * 生成运单
     *
     * @param token
     * @param order_id      订单ID，须保证全局唯一，不超过512字节
     * @param openid        用户openid，
     * @param delivery_id   快递公司ID
     * @param biz_id        快递客户编码或者现付编码
     * @param custom_remark 快递备注信息，比如"易碎物品"，不超过1024字节
     * @param tagid         订单标签id，用于平台型小程序区分平台上的入驻方，tagid须与入驻方账号一一对应，非平台型小程序无需填写该字段
     * @param senderBo      发件人信息
     * @param receiverBo    收件人信息
     * @param cargoBo       包裹信息，将传递给快递公司
     * @param shopBo        商品信息，会展示到物流服务通知和电子面单中
     * @param insuredBo     保价信息
     * @param serviceBo     服务类型
     * @param expect_time   Unix 时间戳, 单位秒，顺丰必须传。 预期的上门揽件时间，0表示已事先约定取件时间；否则请传预期揽件时间戳，需大于当前时间，收件员会在预期时间附近上门。例如expect_time为“1557989929”，表示希望收件员将在2019年05月16日14:58:49-15:58:49内上门取货。说明：若选择 了预期揽件时间，请不要自己打单，由上门揽件的时候打印。
     * @return
     * @throws Exception
     */
    public static JSONObject addOrder(String token, String add_source, String wx_appid, String order_id, String openid, String delivery_id, String biz_id, String custom_remark,
                                      String tagid, @Validated WeChatExpressSenderBo senderBo, @Validated WeChatExpressReceiverBo receiverBo,
                                      @Validated WeChatExpressCargoBo cargoBo, @Validated WeChatExpressShopBo shopBo,
                                      @Validated WeChatExpressInsuredBo insuredBo, @Validated WeChatExpressServiceBo serviceBo,
                                      Integer expect_time) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("add_source", add_source);
        jsonObject.put("wx_appid", wx_appid);
        jsonObject.put("order_id", order_id);
        jsonObject.put("openid", openid);
        jsonObject.put("delivery_id", delivery_id);
        jsonObject.put("biz_id", biz_id);
        jsonObject.put("custom_remark", custom_remark);
        jsonObject.put("tagid", tagid);
        JSONObject sender = new JSONObject();
        sender.put("name", senderBo.getName());
        sender.put("tel", senderBo.getTel());
        sender.put("mobile", senderBo.getMobile());
        sender.put("province", senderBo.getProvince());
        sender.put("city", senderBo.getCity());
        sender.put("area", senderBo.getArea());
        sender.put("address", senderBo.getAddress());
        jsonObject.put("sender", sender);
        JSONObject receiver = new JSONObject();
        receiver.put("name", receiverBo.getName());
        receiver.put("tel", receiverBo.getTel());
        receiver.put("mobile", receiverBo.getMobile());
        receiver.put("province", receiverBo.getProvince());
        receiver.put("city", receiverBo.getCity());
        receiver.put("area", receiverBo.getArea());
        receiver.put("address", receiverBo.getAddress());
        jsonObject.put("receiver", receiver);
        JSONObject cargo = new JSONObject();
        cargo.put("count", cargoBo.getCount());
        cargo.put("weight", cargoBo.getWeight());
        cargo.put("space_x", cargoBo.getSpaceX());
        cargo.put("space_y", cargoBo.getSpaceY());
        cargo.put("space_z", cargoBo.getSpaceZ());
        JSONArray detailList = new JSONArray();
        for (WeChatExpressCargoDetailBo cargoDetailBo : cargoBo.getDetailList()) {
            JSONObject cargoDetail = new JSONObject();
            cargoDetail.put("count", cargoDetailBo.getCount());
            cargoDetail.put("name", cargoDetailBo.getName());
            detailList.add(cargoDetail);
        }
        cargo.put("detail_list", detailList);
        jsonObject.put("cargo", cargo);
        JSONObject shop = new JSONObject();
        shop.put("wxa_path", shopBo.getWxaPath());
        shop.put("img_url", shopBo.getImgUrl());
        shop.put("goods_name", shopBo.getGoodsName());
        shop.put("goods_count", shopBo.getGoodsCount());
        jsonObject.put("shop", shop);
        JSONObject insured = new JSONObject();
        insured.put("use_insured", insuredBo.getUseInsured());
        insured.put("insured_value", insuredBo.getInsuredValue());
        jsonObject.put("insured", insured);
        JSONObject service = new JSONObject();
        service.put("service_type", serviceBo.getServiceType());
        service.put("service_name", serviceBo.getServiceName());
        jsonObject.put("service", service);
        jsonObject.put("expect_time", expect_time);
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/order/add?access_token=";
        return WeChatUtils.callApi(url, token, jsonObject);
    }


    /**
     * 取消运单
     *
     * @param token
     * @param order_id    订单 ID，需保证全局唯一
     * @param openid      用户openid
     * @param delivery_id 快递公司ID
     * @param waybill_id  运单ID
     * @return
     * @throws Exception
     */
    public static JSONObject cancelOrder(String token, String order_id, String openid, String delivery_id, String waybill_id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("order_id", order_id);
        jsonObject.put("openid", openid);
        jsonObject.put("delivery_id", delivery_id);
        jsonObject.put("waybill_id", waybill_id);
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/order/cancel?access_token=";
        return WeChatUtils.callApi(url, token, jsonObject);
    }


    /**
     * 获取支持的快递公司列表
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static JSONObject getAllDelivery(String token) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/delivery/getall?access_token=";
        String str = HttpSendUtils.sendPostJson(url + token, jsonObject);
        JSONObject rs = JSONObject.parseObject(str);
        return rs;
    }


    /**
     * 查询运单轨迹
     *
     * @param token
     * @param order_id    订单 ID，需保证全局唯一
     * @param openid      用户openid
     * @param delivery_id 快递公司ID
     * @param waybill_id  运单ID
     * @return
     * @throws Exception
     */
    public static JSONObject getPath(String token, String order_id, String openid, String delivery_id, String waybill_id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("order_id", order_id);
        jsonObject.put("openid", openid);
        jsonObject.put("delivery_id", delivery_id);
        jsonObject.put("waybill_id", waybill_id);
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/path/get?access_token=";
        String str = HttpSendUtils.sendPostJson(url + token, jsonObject);
        JSONObject rs = JSONObject.parseObject(str);
        return rs;
    }


    /**
     * 获取运单数据
     *
     * @param token
     * @param order_id    订单 ID，需保证全局唯一
     * @param openid      用户openid
     * @param delivery_id 快递公司ID
     * @param waybill_id  运单ID
     * @return
     * @throws Exception
     */
    public static JSONObject getOrder(String token, String order_id, String openid, String delivery_id, String waybill_id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("order_id", order_id);
        jsonObject.put("openid", openid);
        jsonObject.put("delivery_id", delivery_id);
        jsonObject.put("waybill_id", waybill_id);
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/order/get?access_token=";
        return WeChatUtils.callApi(url, token, jsonObject);
    }


    /**
     * 获取运单数据
     *
     * @param token
     * @param orderBoList 订单列表, 最多不能超过100个
     * @return
     * @throws Exception
     */
    public static JSONObject batchGetOrder(String token, List<WeChatExpressOrderBo> orderBoList) throws Exception {
        JSONObject jsonObject = new JSONObject();
        JSONArray orderList = new JSONArray();
        for (WeChatExpressOrderBo orderBo : orderBoList) {
            JSONObject order = new JSONObject();
            order.put("order_id", orderBo.getOrderId());
            order.put("delivery_id", orderBo.getDeliveryId());
            order.put("waybill_id", orderBo.getWaybillId());
            orderList.add(order);
        }
        jsonObject.put("order_list", orderList);
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/order/batchget?access_token=";
        return WeChatUtils.callApi(url, token, jsonObject);
    }


    /**
     * 获取电子面单余额。仅在使用加盟类快递公司时，才可以调用。
     *
     * @param token
     * @param delivery_id 快递公司ID
     * @param biz_id      快递公司客户编码
     * @return
     * @throws Exception
     */
    public static JSONObject getQuota(String token, String delivery_id, String biz_id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("delivery_id", delivery_id);
        jsonObject.put("biz_id", biz_id);
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/quota/get?access_token=";
        return WeChatUtils.callApi(url, token, jsonObject);
    }


    /**
     * 配置面单打印员，可以设置多个，若需要使用微信打单 PC 软件，才需要调用。
     *
     * @param token
     * @param openid      打印员 openid
     * @param update_type 更新类型
     * @param tagid_list  用于平台型小程序设置入驻方的打印员面单打印权限，同一打印员最多支持10个tagid，使用逗号分隔，如填写123，456，表示该打印员可以拉取到tagid为123和456的下的单，非平台型小程序无需填写该字段
     * @return
     * @throws Exception
     */
    public static JSONObject updatePrinter(String token, String openid, String update_type, String tagid_list) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid", openid);
        jsonObject.put("update_type", update_type);
        jsonObject.put("tagid_list", tagid_list);
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/printer/update?access_token=";
        return WeChatUtils.callApi(url, token, jsonObject);
    }


    /**
     * 获取打印员。若需要使用微信打单 PC 软件，才需要调用。
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static JSONObject getPrinter(String token) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/printer/getall?access_token=";
        return WeChatUtils.callApi(url, token, jsonObject);
    }


    /**
     * 模拟快递公司更新订单状态, 该接口只能用户测试
     *
     * @param token
     * @param biz_id      商户id,需填test_biz_id
     * @param order_id    订单号
     * @param delivery_id 快递公司id,需填TEST
     * @param waybill_id  运单号
     * @param action_time 轨迹变化 Unix 时间戳
     * @param action_type 轨迹变化类型
     * @param action_msg  轨迹变化具体信息说明,使用UTF-8编码
     * @return
     * @throws Exception
     */
    public static JSONObject testUpdateOrder(String token, String biz_id, String order_id, String delivery_id, String waybill_id, Integer action_time,
                                             Integer action_type, String action_msg) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String url = "https://api.weixin.qq.com/cgi-bin/express/business/test_update_order?access_token=";
        return WeChatUtils.callApi(url, token, jsonObject);
    }

}
