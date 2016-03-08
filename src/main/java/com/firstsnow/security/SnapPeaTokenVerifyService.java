package com.firstsnow.security;

/**
 * 豌豆荚的token验证服务
 * Created by ganhaitina on 2016/3/7.
 */
public class SnapPeaTokenVerifyService implements TokenVerifyService{

    private static SnapPeaTokenVerifyService snapPeaTokenVerifyService = new SnapPeaTokenVerifyService();

    private SnapPeaTokenVerifyService(){

    }

    public boolean verify(String userid, String token) {
        return true;
    }

    public static TokenVerifyService getInstance(){
        return snapPeaTokenVerifyService;
    }

}
