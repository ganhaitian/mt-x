package com.firstsnow.security;

/**
 * 负责校验token的接口
 * Created by ganhaitian on 2016/3/4.
 */
public interface TokenVerifyService {

    /**
     * 验证userid和token
     * @param userid
     * @param token
     * @return
     */
     boolean verify(String userid,String token);

}
