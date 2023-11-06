package com.yin.pddserver.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethodEnum;
import com.ijpay.core.kit.AesUtil;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxDomainEnum;
import com.ijpay.wxpay.enums.v3.BasePayApiEnum;
import com.ijpay.wxpay.enums.v3.OtherApiEnum;
import com.yin.pddserver.common.exceptions.MessageException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WxPayUtil {

    public static Map<String, String> weixinPrePay(String appId, String mchId, String paySecret, String trade_no, BigDecimal totalAmount, String description, String attach, String openid, String wxnotify, String spbill_create_ip,
                                                   String privateKeyPath, String certPath, String platformCertPath) throws Exception {
        com.alibaba.fastjson.JSONObject parameter = new com.alibaba.fastjson.JSONObject();
        parameter.put("appid", appId);
        parameter.put("mchid", mchId);
        parameter.put("description", description);
        parameter.put("out_trade_no", trade_no);
        parameter.put("notify_url", wxnotify);
        parameter.put("attach", attach);
        com.alibaba.fastjson.JSONObject amount = new com.alibaba.fastjson.JSONObject();
        parameter.put("amount", amount);
        amount.put("total", totalAmount.multiply(BigDecimal.valueOf(100)).intValue());
        com.alibaba.fastjson.JSONObject payer = new com.alibaba.fastjson.JSONObject();
        parameter.put("payer", payer);
        payer.put("openid", openid);
        IJPayHttpResponse response = WxPayApi.v3(RequestMethodEnum.POST, WxDomainEnum.CHINA.getDomain(), BasePayApiEnum.JS_API_PAY.getUrl(), mchId, getSerialNumber(certPath), null, privateKeyPath, parameter.toJSONString());
        Map<String, String> map = new HashMap<>();
        if (response.getStatus() == 200) {
            boolean verifySignature = WxPayKit.verifySignature(response, platformCertPath);
            log.info("verifySignature: {}", verifySignature);
            if (verifySignature) {
                String body = response.getBody();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                String prepayId = jsonObject.getStr("prepay_id");
                // 私钥
                map = WxPayKit.jsApiCreateSign(appId, prepayId, privateKeyPath);
                map.put("prepay_id", prepayId);
                log.info("唤起支付参数:{}", map);
            }
        }
        return map;
    }

    public static void updateCertificates(String mchId, String paySecret, String privateKeyPath, String certPath, String platformCertPath) throws Exception {
        // 获取平台证书列表
        IJPayHttpResponse response = WxPayApi.v3(RequestMethodEnum.GET, WxDomainEnum.CHINA.getDomain(), OtherApiEnum.GET_CERTIFICATES.getUrl(), mchId, getSerialNumber(certPath), null, privateKeyPath, "");
        String body = response.getBody();
        int status = response.getStatus();
        if (status == 200) {
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            JSONObject encryptObject = dataArray.getJSONObject(0);
            JSONObject encryptCertificate = encryptObject.getJSONObject("encrypt_certificate");
            String associatedData = encryptCertificate.getStr("associated_data");
            String cipherText = encryptCertificate.getStr("ciphertext");
            String nonce = encryptCertificate.getStr("nonce");
            String serialNo = encryptObject.getStr("serial_no");
            final String platSerialNo = savePlatformCert(associatedData, paySecret, nonce, cipherText, platformCertPath);
            log.info("平台证书序列号: {} serialNo: {}", platSerialNo, serialNo);
        }
        // 根据证书序列号查询对应的证书来验证签名结果
        boolean verifySignature = WxPayKit.verifySignature(response, platformCertPath);
        System.out.println("verifySignature:" + verifySignature);
        if (!verifySignature) {
            throw new MessageException("平台证书签名失败，请检查V3秘钥和证书");
        }
    }


    public void callBack(HttpServletRequest request, HttpServletResponse response) {
        log.info("收到微信支付回调");
        Map<String, String> map = new HashMap<>(12);
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");

            log.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);
            String result = HttpKit.readData(request);
            log.info("支付通知密文 {}", result);
            // 根据证书序列号查询对应的证书来验证签名结果
            String platformCertPath = "D:\\MyTools\\WXWork\\WxPayFile\\cert.pem";
            //这个商户号对应的那个V3秘钥
            String mckKey = "cjajsrtasdqw21523asdf1";
            //需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp, mckKey, platformCertPath);
            log.info("支付通知明文 {}", plainText);
            //这个就是具体的业务情况
            savePayPlainText(plainText);
            //回复微信
            if (StrUtil.isNotEmpty(plainText)) {
                response.setStatus(200);
                map.put("code", "SUCCESS");
                map.put("message", "SUCCESS");
            } else {
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message", "签名错误");
            }
            response.setHeader("Content-type", ContentType.JSON.toString());
            response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String savePlatformCert(String associatedData, String apiKey3, String nonce, String cipherText, String certPath) {
        try {
            File file = new File(certPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            AesUtil aesUtil = new AesUtil(apiKey3.getBytes(StandardCharsets.UTF_8));
            // 平台证书密文解密
            // encrypt_certificate 中的  associated_data nonce  ciphertext
            String publicKey = aesUtil.decryptToString(
                    associatedData.getBytes(StandardCharsets.UTF_8),
                    nonce.getBytes(StandardCharsets.UTF_8),
                    cipherText
            );
            FileOutputStream fos = new FileOutputStream(certPath);
            fos.write(publicKey.getBytes());
            fos.close();
            // 获取平台证书序列号
            X509Certificate certificate = PayKit.getCertificate(new ByteArrayInputStream(publicKey.getBytes()));
            return certificate.getSerialNumber().toString(16).toUpperCase();
        } catch (Exception e) {
            log.error("写入证书错误:{}", e);
            return e.getMessage();
        }
    }

    /**
     * 保存订单的支付通知明文
     *
     * @param plainText
     */
    private void savePayPlainText(String plainText) {
        JSONObject jsonObject = JSONUtil.parseObj(plainText);
        //这个就是发起订单时的那个订单号
        String outTradeNo = jsonObject.getStr("out_trade_no");
        //todo 把微信支付回调的明文消息存进数据库，方便后续校验查看

        //todo 把微信支付后需要处理的具体业务处理了
    }

    /**
     * 获取证书序列号
     *
     * @return
     */
    private static String getSerialNumber(String certPath) throws MessageException {
        X509Certificate certificate = PayKit.getCertificate(FileUtil.getInputStream(certPath));
        String serialNo = certificate.getSerialNumber().toString(16).toUpperCase();
        boolean isValid = PayKit.checkCertificateIsValid(certificate, certPath, -2);
        if (isValid) {
            throw new MessageException("证书即将到期，请更换支付证书");
        }
        return serialNo;
    }


}
