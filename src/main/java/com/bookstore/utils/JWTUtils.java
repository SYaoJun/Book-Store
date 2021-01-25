package com.bookstore.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author yaojun
 * @create 2021-01-24 14:56
 */
public class JWTUtils {
    private static final String SIGN = "asdfasdfasdf";
    /**
     *生成token
     */
    public static String getToken(Map<String, String> map){
        Calendar instance = Calendar.getInstance();
        /*默认七天过期*/
        instance.add(Calendar.DATE, 7);
        //创建builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k , v)-> {
            builder.withClaim(k, v);
        });
        //signature
        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGN));
        return token;
    }
    /**
     *验证token
     */
    public static DecodedJWT verifyToken(String token){
        return JWT.require(Algorithm.HMAC256("asdfasdfasdf")).build().verify(token);
    }
}
