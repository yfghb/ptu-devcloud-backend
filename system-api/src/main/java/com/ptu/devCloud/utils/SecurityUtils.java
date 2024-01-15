package com.ptu.devCloud.utils;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.ptu.devCloud.entity.LoginUser;
import com.ptu.devCloud.exception.JobException;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Yang Fan
 * @since 2023/11/22 17:21
 */
public class SecurityUtils
{
    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 设置Authentication
     */
    public static void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    /**
     * 对明文加密
     *
     * @param content 明文
     * @param key     密钥
     * @return String 加密后的字符串
     */
    public static String aesEncrypt(String content, String key) {
        //加密为16进制表示
        return getAes(key).encryptHex(content);
    }

    /**
     * 对密文解密
     *
     * @param encryptContent 密文
     * @param key            密钥
     * @return String 解密后的字符串
     */
    public static String aesDecrypt(String encryptContent, String key) {
        // 解密为字符串
        return getAes(key).decryptStr(encryptContent, CharsetUtil.CHARSET_UTF_8);
    }


    private static AES getAes(@NonNull String key) {
        if (key.length() != 16) {
            throw new JobException("密钥长度需为16位");
        }
        //构建
        return new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
    }


}