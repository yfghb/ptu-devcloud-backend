package com.ptu.devCloud.utils;

import com.alibaba.fastjson.JSON;
import com.ptu.devCloud.constants.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtUtil {
    /**
     * 生成 jwt token
     * @param data 数据
     * @return token
     */
    public static String generate(Object data)  {
        String json = JSON.toJSONString(data);
        return  Jwts.builder()
                .setSubject(json)
                .signWith(SignatureAlgorithm.HS256, CommonConstants.SECRET_KEY)
                .compact();
    }

    /**
     * 解析 jwt token 获取 body
     * @param token token
     * @return 被解密的内容
     */
    public static Claims parse(String token) {
        return Jwts.parser()
                .setSigningKey(CommonConstants.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }


}


