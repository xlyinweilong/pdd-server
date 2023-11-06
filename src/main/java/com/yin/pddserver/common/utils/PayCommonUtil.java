package com.yin.pddserver.common.utils;

import com.yin.pddserver.common.exceptions.MessageException;
import com.yin.pddserver.common.utils.wxpay.WXMyConfigUtil;
import com.yin.pddserver.common.utils.wxpay.WXPay;
import com.yin.pddserver.common.utils.wxpay.WXPayUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.util.DigestUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.util.*;

public class PayCommonUtil {

    //微信参数配置
//    public static String API_KEY = "473872880a1e3bde3efaf0c7c78ad4ae";
//    public static String APPID = "wx05b66ca078137785";
//    public static String MCH_ID = "1560395801";

    /**
     * 微信公众号支付
     *
     * @param trade_no    订单号
     * @param totalAmount 支付金额
     * @param description 文字内容说明
     * @param attach      自定义参数 length=127
     * @param openId      微信公众号openId
     * @param wxnotify    回调地址
     * @return -
     */
    public static Map<String, String> WxPublicPay(String appId, String mchId, String paySecret, String trade_no, BigDecimal totalAmount, String description, String attach, String openId, String wxnotify, String spbill_create_ip) {
        Map<String, String> map = weixinPrePay(appId, mchId, paySecret, trade_no, totalAmount, description, attach, openId, wxnotify, spbill_create_ip);
        Map<String, String> finalpackage = new TreeMap<>();
        finalpackage.put("appId", appId);
        finalpackage.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
        finalpackage.put("nonceStr", getRandomString(32));
        finalpackage.put("package", "prepay_id=" + map.get("prepay_id"));
        finalpackage.put("signType", "MD5");
        String sign = PayCommonUtil.createSign(paySecret, finalpackage);
        finalpackage.put("prepay_id", map.get("prepay_id"));
        finalpackage.put("paySign", sign);
        return finalpackage;
    }

    public static Map<String, String> WxPublicPayV3(String appId, String mchId, String paySecret, String trade_no, BigDecimal totalAmount, String description, String attach, String openId, String wxnotify, String spbill_create_ip, String privateKeyPath, String certPath, String platformCertPath) throws Exception {
        return WxPayUtil.weixinPrePay(appId, mchId, paySecret, trade_no, totalAmount, description, attach, openId, wxnotify, spbill_create_ip, privateKeyPath, certPath, platformCertPath);
    }

    /**
     * -
     *
     * @param trade_no    订单号
     * @param totalAmount 支付金额
     * @param description 文字内容说明
     * @param attach      自定义参数 length=127
     * @param openid      微信公众号openId
     * @param wxnotify    回调地址
     * @return -
     */
    private static Map<String, String> weixinPrePay(String appId, String mchId, String paySecret, String trade_no, BigDecimal totalAmount, String description, String attach, String openid, String wxnotify, String spbill_create_ip) {
        Map<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("appid", appId);
        parameterMap.put("mch_id", mchId);
        parameterMap.put("nonce_str", getRandomString(32));
        parameterMap.put("body", description);
        parameterMap.put("attach", attach);
        parameterMap.put("out_trade_no", trade_no);
        parameterMap.put("fee_type", "CNY");
        BigDecimal total = totalAmount.multiply(BigDecimal.valueOf(100));
        java.text.DecimalFormat df = new java.text.DecimalFormat("0");
        parameterMap.put("total_fee", df.format(total));
        parameterMap.put("spbill_create_ip", spbill_create_ip);
        parameterMap.put("notify_url", wxnotify);
        parameterMap.put("trade_type", "JSAPI");
        //trade_type为JSAPI是 openid为必填项
        parameterMap.put("openid", openid);
        String sign = PayCommonUtil.createSign(paySecret, parameterMap);
        parameterMap.put("sign", sign);
        String requestXML = PayCommonUtil.getRequestXml(parameterMap);
        String result = PayCommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);
        Map<String, String> map = null;
        try {
            map = PayCommonUtil.doXMLParse(result);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> weixinPreRefure(String filePath, String appId, String mchId, String paySecret, String trade_no, BigDecimal totalAmount, BigDecimal totalRefure, String out_refund_no, String wxnotify) throws Exception {
        BigDecimal total = totalAmount.multiply(BigDecimal.valueOf(100));
        BigDecimal refure = totalRefure.multiply(BigDecimal.valueOf(100));
        java.text.DecimalFormat df = new java.text.DecimalFormat("0");
        Map<String, String> data = new TreeMap<>();
        WXMyConfigUtil config = new WXMyConfigUtil(appId, mchId, paySecret, filePath);
//        WXMyConfigUtil config = new WXMyConfigUtil(appId, mchId, paySecret, "/data/wx_pay_cert/apiclient_cert.p12");
//        WXMyConfigUtil config = new WXMyConfigUtil(appId, mchId, paySecret, "F:/WXCertUtil/cert/昕逸/1560395801_20200209_cert/apiclient_cert.p12");
        WXPay wxpay = new WXPay(config);
        data.put("out_refund_no", out_refund_no);
        data.put("out_trade_no", trade_no);
        data.put("total_fee", df.format(total));
        data.put("refund_fee", df.format(refure));
        data.put("appid", appId);
        data.put("mch_id", mchId);
//        data.put("notify_url", wxnotify);
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("sign", createSign(paySecret, data));
        Map<String, String> resp = null;
        try {
            resp = wxpay.refund(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println(resp);
        String return_code = resp.get("return_code");
        String return_msg = resp.get("return_msg");
        String resultReturn = null;
        if ("SUCCESS".equals(return_code)) {
            String result_code = resp.get("result_code");
            String err_code_des = resp.get("err_code_des");
            if ("SUCCESS".equals(result_code)) {
                //表示退款申请接受成功，结果通过退款查询接口查询
                //修改用户订单状态为退款申请中（暂时未写）
                resultReturn = "退款申请成功";
            } else {
                resultReturn = err_code_des;
                throw new MessageException("退款申请失败;" + err_code_des + ";" + return_msg);
            }
        } else {
            resultReturn = return_msg;
            throw new MessageException("退款申请失败：" + return_msg);
        }
        return resp;

    }

    //随机字符串生成
    private static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //请求xml组装
    private static String getRequestXml(Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<").append(key).append(">").append("<![CDATA[").append(value).append("]]></").append(key).append(">");
            } else {
                sb.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    //生成签名
    private static String createSign(String paySecret, Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        Set es = parameters.entrySet();
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(paySecret);
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes()).toUpperCase();
    }

    /**
     * 验证回调签名
     *
     * @param map
     * @return
     */
    public static boolean isTenpaySign(String paySecret, Map<String, String> map) {
        String charset = "utf-8";
        String signFromAPIResponse = map.get("sign");
        if (signFromAPIResponse == null || signFromAPIResponse.equals("")) {
            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
//        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
        //过滤空 设置 TreeMap
        SortedMap<String, String> packageParams = new TreeMap<>();
        for (String parameter : map.keySet()) {
            String parameterValue = map.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        StringBuilder sb = new StringBuilder();
        Set es = packageParams.entrySet();
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(paySecret);
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        //算出签名
        String tobesign = sb.toString();
        String resultSign = DigestUtils.md5DigestAsHex(tobesign.getBytes()).toUpperCase();
        String tenpaySign = packageParams.get("sign").toUpperCase();
        return tenpaySign.equals(resultSign);
    }

    //请求方法
    private static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuilder buffer = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}" + ce);
        } catch (Exception e) {
            System.out.println("https请求异常：{}" + e);
        }
        return null;
    }

    //退款的请求方法
    public static String httpsRequest2(String requestUrl, String requestMethod, String outputStr) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        StringBuilder res = new StringBuilder("");
        try (FileInputStream instream = new FileInputStream(new File("F:/WXCertUtil/cert/1560395801_20200209_cert/apiclient_cert.p12"))) {
            keyStore.load(instream, "".toCharArray());
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "1313329201".toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build()) {
            HttpPost httpost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            StringEntity entity2 = new StringEntity(outputStr, Consts.UTF_8);
            httpost.setEntity(entity2);
            System.out.println("executing request" + httpost.getRequestLine());
            try (CloseableHttpResponse response = httpclient.execute(httpost)) {
                HttpEntity entity = response.getEntity();
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text = "";
                    res.append(text);
                    while ((text = bufferedReader.readLine()) != null) {
                        res.append(text);
                        System.out.println(text);
                    }
                }
                EntityUtils.consume(entity);
            }
        }
        return res.toString();

    }

    //xml解析
    public static Map doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        for (Object aList : list) {
            Element e = (Element) aList;
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }
        //关闭流
        in.close();
        return m;
    }

    private static String getChildrenText(List children) {
        StringBuilder sb = new StringBuilder();
        if (!children.isEmpty()) {
            for (Object aChildren : children) {
                Element e = (Element) aChildren;
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<").append(name).append(">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</").append(name).append(">");
            }
        }

        return sb.toString();
    }
}
