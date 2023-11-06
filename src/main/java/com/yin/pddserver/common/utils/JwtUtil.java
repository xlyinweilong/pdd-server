package com.yin.pddserver.common.utils;

import com.yin.pddserver.common.auth.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

    private static final String SECRET = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInBhc3N3b3JkIjoiMjI3YmQwYjQ0MDA4NjcyMTg0ZTUwMzY2ZWQyOTExMjkiLCJpYXQiOjE1ODQ5NDA0NDEsImp0aSI6ImQzMzNmNTJhLTc0NjQtNGYxMS04NzhmLTI5MTJhNzM1ZWE1YiIsInVzZXJuYW1lIjoiYWRtaW4ifQ.d7NBEfz5xlVclog4YtapFbjN2OljOr9jyD_LpJ769Ec";

    /**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     *
     * @param user 登录成功的user对象
     * @return
     */
    public static String createJWT(LoginUser user) {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成JWT的时间
        Date now = new Date();

        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("password", user.getSecret());
        claims.put("platform", user.getPlatform());
        claims.put("tn_id", user.getTnId());
        claims.put("create_date", user.getCreateDate());

        //生成签发人
        String subject = user.getPlatform() + user.getUsername();

        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, SECRET);
        return builder.compact();
    }


    /**
     * Token的解密
     *
     * @param token 加密后的token
     * @return
     */
    public static Claims parseJWT(String token) {
        try {
            if (StringUtils.isBlank(token)) {
                return null;
            }
            Claims claims = Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(SECRET)
                    //设置需要解析的jwt
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

    public static LoginUser getLoginUser(Claims claims) {
        if (claims == null) {
            return null;
        }
        String password = claims.get("password", String.class);
        LoginUser loginUser = new LoginUser(getUserPlatform(claims), getUserId(claims), getUsername(claims), getTnId(claims), password);
        return loginUser;
    }


    public static String getUserId(Claims claims) {
        return claims.get("id", String.class);
    }

    public static String getUsername(Claims claims) {
        return claims.get("username", String.class);
    }

    public static String getUserSecret(Claims claims) {
        return claims.get("password", String.class);
    }

    public static String getUserPlatform(Claims claims) {
        return claims.get("platform", String.class);
    }

    public static String getTnId(Claims claims) {
        return claims.get("tn_id", String.class);
    }

    public static Date getCreateDate(Claims claims) {
        return claims.get("create_date", Date.class);
    }

}
