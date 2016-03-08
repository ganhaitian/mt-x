package com.firstsnow.security;

/**
 * 平台
 * Created by ganhaitian on 2016/3/7.
 */
public enum Platform {

    // 豌豆荚
    SnapPea(1){
        public TokenVerifyService getTokenVerifyService(){
            return SnapPeaTokenVerifyService.getInstance();
        }
    };

    private int code;

    private Platform(int code){
        this.code = code;
    }

    public static Platform getByCode(int code){
        for(Platform p:Platform.values()){
            if(p.code == code)
                return p;
        }
        return null;
    }

    public TokenVerifyService getTokenVerifyService(){
        return null;
    }

}
