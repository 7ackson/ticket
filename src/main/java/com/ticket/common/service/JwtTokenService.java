package com.ticket.common.service;


import cn.hutool.core.codec.Base64;
import com.ticket.common.constant.Constants;
import com.ticket.common.security.model.LoginUserModel;
import com.ticket.common.utils.ServletUtils;
import com.ticket.common.utils.StringUtils;
import com.ticket.common.utils.ip.AddressUtils;
import com.ticket.common.utils.ip.IpUtils;
import com.ticket.common.utils.uuid.IdUtils;
import com.ticket.constant.SysConstants;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: JWT工具类
 * @author: ye wei
 * @create: 2022/07/08 10:54
 */
@Slf4j
@Component
public class JwtTokenService {

    @Autowired
    RedisService redisService;

    /**
     * redis token 时间刷新时间
     */
    private static final long MILLIS_MINUTE_TEN = 20 * Constants.MINUTE * Constants.MILLIS_SECOND;

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expirationTime}")
    private Long expirationTime;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.redisSaveTime}")
    private Long redisSaveTime;
    @Value("${jwt.oneAccountMoreToken}")
    private boolean oneAccountMoreToken;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public Long getRedisSaveTime() {
        return redisSaveTime;
    }

    public void setRedisSaveTime(Long redisSaveTime) {
        this.redisSaveTime = redisSaveTime;
    }

    public boolean isOneAccountMoreToken() {
        return oneAccountMoreToken;
    }

    public void setOneAccountMoreToken(boolean oneAccountMoreToken) {
        this.oneAccountMoreToken = oneAccountMoreToken;
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createJwtToken(Map<String, Object> claims) {
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, decodeSecretKey());
        return builder.compact();
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey decodeSecretKey() {
        byte[] encodedKey = Base64.decode(getSecretKey());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解密jwt从令牌中获取数据声明
     *
     * @param jwsToken
     * @return
     */
    private Claims parseJWT(String jwsToken) {
        try {
            return Jwts.parser().setSigningKey(decodeSecretKey()).parseClaimsJws(jwsToken).getBody();
        } catch (Exception e) {
            log.error("jwsToken解析错误:{}", jwsToken);
            return null;
        }
    }

    /**
     * token检验
     *
     * @param jwsToken
     * @return
     */
    private boolean isValid(String jwsToken) {
        try {
            SecretKey key = decodeSecretKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (StringUtils.isNotEmpty(token) && token.startsWith(SysConstants.TOKEN_PREFIX)) {
            token = token.replace(SysConstants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 获取存入redis的key
     *
     * @param keySuffix
     * @return
     */
    private String getRedisTokenKey(String keySuffix) {
        return SysConstants.LOGIN_TOKEN_KEY + keySuffix;
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUserModel loginUser) {

        //同一账户单点登录，使用用户ID作为后缀，采用单点登录，同一个用户只有一个RedisKey
        String loginMark = loginUser.getUserId().toString();
        if (isOneAccountMoreToken()) {
            //同一账户多点登录
            loginMark = IdUtils.fastSimpleUUID();
        }

        String tokenUUID = IdUtils.fastUUID();
        Map<String, Object> claims = new HashMap<>();
        claims.put(SysConstants.LOGIN_USER_KEY, loginMark);
        claims.put(SysConstants.LOGIN_USER_LOGIN_UNIQUE_KEY, tokenUUID);
        String token = createJwtToken(claims);

        loginUser.setTokenUuid(tokenUUID);
        loginUser.setLoginMark(loginMark);
        //获取头信息
        setUserAgent(loginUser);
        //存入redis
        saveToken(loginUser);
        return token;
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUserModel loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 保存令牌
     *
     * @param loginUser 登录信息
     */
    public void saveToken(LoginUserModel loginUser) {
        if (loginUser.getLoginTime() == null) {
            loginUser.setLoginTime(System.currentTimeMillis());
        }
        loginUser.setSaveTime(System.currentTimeMillis());
        // 根据uuid将loginUser缓存
        String userRedisKey = getRedisTokenKey(loginUser.getLoginMark());
        redisService.set(userRedisKey, loginUser, getRedisSaveTime(), TimeUnit.SECONDS);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void refreshToken(LoginUserModel loginUser) {
        String userRedisKey = getRedisTokenKey(loginUser.getLoginMark());
        long keyExpTime = redisService.getExpire(userRedisKey);
        /**
         *返回 -2   表示这个key已过期，已不存在
         *返回 -1   表示这个key没有设置有效期
         *返回0以上的值   表示是这个key的剩余有效时间
         */
        if (keyExpTime == -2) {
            saveToken(loginUser);
        } else if (keyExpTime > 0 && keyExpTime < (20 * Constants.MINUTE)) {
            //更新时间
            redisService.expire(userRedisKey, getRedisSaveTime());
        }
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserModel getLoginUser(HttpServletRequest request, boolean isCheckTokenUUID) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseJWT(token);
                if (StringUtils.isNull(claims)) {
                    return null;
                }
                // 解析对应的权限以及用户信息
                String tokenSuffix = (String) claims.get(SysConstants.LOGIN_USER_KEY);
                String tokenUUID = (String) claims.get(SysConstants.LOGIN_USER_LOGIN_UNIQUE_KEY);
                String tokenKey = getRedisTokenKey(tokenSuffix);
                LoginUserModel user = redisService.getObject(tokenKey);
                if (isCheckTokenUUID && StringUtils.isNotNull(user) && !tokenUUID.equals(user.getTokenUuid())) {
                    return null;
                }
                return user;
            } catch (Exception e) {
                log.error("getLoginUser异常:{}", e.getMessage());
            }
        }
        return null;
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String tokenSuffix) {
        if (StringUtils.isNotEmpty(tokenSuffix)) {
            String tokenKey = getRedisTokenKey(tokenSuffix);
            redisService.del(tokenKey);
        }
    }

}
