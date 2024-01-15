package com.ptu.devCloud.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ptu.devCloud.constants.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class JwtUtil {

    private static final long DAY = 24 * 60 * 60 * 1000;

    /**
     * 生成 jwt token 过期时间为次日四点
     * @author Yang Fan
     * @since 2023/11/16 9:47
     * @param data 数据
     * @return token
     */
    public static String generate(Object data)  {
        String json = SecurityUtils.aesEncrypt(JSON.toJSONString(data, true), CommonConstants.SECRET_KEY_16);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.DATE, 1);
        try {
            // 设置次日4点token过期
            Date endDate = dateFormat.parse(dateFormat.format(calendar.getTime()).substring(0,11) + "04:00:00");
            return  Jwts.builder()
                    .setSubject(json)
                    .setExpiration(endDate)
                    .signWith(SignatureAlgorithm.HS256, CommonConstants.SECRET_KEY_16)
                    .compact();
        }
        catch (ParseException e) {
            log.warn("JwtUtil 时间解析异常, token过期修改成默认24小时期");
            return  Jwts.builder()
                    .setSubject(json)
                    .setExpiration(calendar.getTime())
                    .signWith(SignatureAlgorithm.HS256, CommonConstants.SECRET_KEY_16)
                    .compact();
        }
    }

    /**
     * 生成 token, 自定义过期时间
     * @author Yang Fan
     * @since 2023/12/15 9:45
     * @param data 数据
     * @param expiration 过期时间, 若不设置则为默认24小时过期
     * @return token
     */
    public static String generateWithExpiration(Object data, Date expiration)  {
        String json = SecurityUtils.aesEncrypt(JSON.toJSONString(data, true), CommonConstants.SECRET_KEY_16);
        Date now = new Date(System.currentTimeMillis());
        if (expiration == null || expiration.compareTo(now) < 0){
            expiration = new Date(System.currentTimeMillis() + DAY);
        }
        return  Jwts.builder()
                .setSubject(json)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, CommonConstants.SECRET_KEY_16)
                .compact();
    }

    /**
     * 解析 jwt token 获取 body
     * @author Yang Fan
     * @since 2023/11/16 9:47
     * @param token token
     * @return 被解密的内容
     */
    public static Claims parse(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(CommonConstants.SECRET_KEY_16)
                .parseClaimsJws(token)
                .getBody();
        // 解密Subject
        claims.setSubject(SecurityUtils.aesDecrypt(claims.getSubject(), CommonConstants.SECRET_KEY_16));
        return claims;
    }


}


