package com.thuwsy.aoyou.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.thuwsy.aoyou.common.constants.CacheConstants;
import com.thuwsy.aoyou.common.domain.vo.LoginUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: JwtUtil
 * Package: com.thuwsy.aoyou.common.utils
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/21 13:18
 * @Version 1.0
 */
@Component
public class JwtUtil {
    private final String secret = "fjsdlqwriijioqwjrjo"; // JWT签名秘钥
    @Autowired
    private RedisCacheUtil redisCacheUtil;

    /**
     * 创建JWT令牌，会将用户数据存放到Redis中，使用UUID作为Redis中的key
     * 可以方便实现单点登录、踢人下线、查看在线用户等功能
     */
    public String createJwt(LoginUserVO loginUserVO) {
        // 1. 在JWT令牌中只存储用户的uuid
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        loginUserVO.setToken(token); // 保存uuid
        loginUserVO.setLoginTime(System.currentTimeMillis()); // 登录时间
        // 2. 调用刷新Token方法，将用户信息保存到Redis，并设置过期时间
        refreshToken(loginUserVO);
        // 3. 加密算法
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 4. 创建JWT令牌
        return JWT.create().withClaim("token", token).sign(algorithm);
    }

    // 刷新token
    private void refreshToken(LoginUserVO loginUserVO) {
        redisCacheUtil.setCacheObject(
                CacheConstants.LOGIN_USER_KEY + loginUserVO.getToken(),
                loginUserVO,24, TimeUnit.HOURS
        );
    }

    /**
     * 解析请求头中的JWT令牌
     */
    public DecodedJWT resolveJwt(String headerToken) {
        // 1. 将请求头中的内容，去掉"Bearer "前缀，转换为标准的JWT令牌
        String jwt = convertToken(headerToken);
        if (jwt == null) return null;

        // 2. 根据私钥和加密算法，得到JWT校验器
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        // 3. 验证token的签名正确
        try {
            // 如果token被人篡改，则会校验失败，抛出JWTVerificationException
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            return decodedJWT;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    // 将请求头中的内容，去掉"Bearer "前缀，转换为标准的JWT令牌
    private String convertToken(String headerToken) {
        if (headerToken == null || !headerToken.startsWith("Bearer "))
            return null;
        return headerToken.substring(7);
    }

    /**
     * 获取已登录的用户信息
     */
    public LoginUserVO getLoginUser(String headerToken) {
        // 1. 解析请求头中的JWT令牌
        DecodedJWT decodedJWT = resolveJwt(headerToken);
        if (decodedJWT == null) return null;

        // 2. 提取JWT中用户的uuid
        String token = decodedJWT.getClaims().get("token").asString();

        // 3. 从Redis中获取用户信息
        LoginUserVO loginUserVO = redisCacheUtil.getCacheObject(
                CacheConstants.LOGIN_USER_KEY + token);
        if (loginUserVO == null) return null;

        // 4. 刷新令牌的过期时间
        // 为了防止用户正在操作时令牌突然过期
        // 于是在令牌过期时间剩余1小时内，如果用户有操作，则自动续期
        long loginTime = loginUserVO.getLoginTime();
        long currentTime = System.currentTimeMillis();
        long hours = (currentTime - loginTime) / 1000 / 3600;
        if (hours >= 23) {
            loginUserVO.setLoginTime(currentTime);
            refreshToken(loginUserVO);
        }
        return loginUserVO;
    }

    /**
     * 用户退出登录后，从Redis中删除key
     */
    public boolean deleteLoginUser(String headerToken) {
        // 1. 解析请求头中的JWT令牌
        DecodedJWT decodedJWT = resolveJwt(headerToken);
        if (decodedJWT == null) return false;

        // 2. 提取JWT中用户的uuid
        String token = decodedJWT.getClaims().get("token").asString();

        // 3. 删除Redis中的用户信息
        return redisCacheUtil.deleteObject(
                CacheConstants.LOGIN_USER_KEY + token);
    }
}
