package com.biluo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.biluo.model.po.TUser;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt的工具类
 *
 */
public class JWTUtils {
    // JWT的密钥
    public static final String SECRET = "w3osER)wTxc0>pTr03ryP";
    
    // 选择记住我之后的jwt（token）的过期时间
    public static final Long EXPIRE_TIME = 7 * 24 * 60L;

    // 默认jwt（token）的过期时间
    public static final Long DEFAULT_EXPIRE_TIME = 30L;
    

    /**
     * 生成jwt
     *
     * @return
     */
    public static String createJWT(String userJSON) {
        // 组装头数据
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        // 链式编程
        return JWT.create()
                // 头部
                .withHeader(header)
                // 负载(可以有多个)
                .withClaim("user", userJSON)
                // 签名
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证jwt
     *
     */
    public static Boolean verifyJWT(String jwt) {
        try {
            // 使用密钥创建一个jwt验证器
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

            // jwt验证器验证jwt
            jwtVerifier.verify(jwt); // 如果此行代码没有抛出异常，就说明jwt验证通过，抛出异常，就说明jwt验证不通过

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解析jwt
     *
     */
    public static String parseJWT(String jwt) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 使用密钥创建一个jwt验证器对象
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

            // jwt验证器对象验证jwt，得到一个解码后的jwt对象
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

            // 验证通过了，就开始解析负载里面的数据
            Claim userJSON = decodedJWT.getClaim("user");

            return userJSON.asString();
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static TUser parseJWTGetUser(String jwt) {
        String userJSON = parseJWT(jwt);
        return JsonUtil.toObject(userJSON, TUser.class);
    }
}