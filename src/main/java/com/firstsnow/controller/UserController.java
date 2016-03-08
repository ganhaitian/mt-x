package com.firstsnow.controller;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 用户
 * Created by lenovo on 2016/1/8.
 */
@Controller
public class UserController {

    ///public void syncUserInfo
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/loginSuccess")
    public
    @ResponseBody
    Map<String,Object> loginSuccess(){
        Map<String,Object> result = Maps.newHashMap();
        result.put("success",true);
        return result;
    }
}
