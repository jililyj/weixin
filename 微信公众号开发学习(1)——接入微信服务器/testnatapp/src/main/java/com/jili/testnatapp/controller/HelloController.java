package com.jili.testnatapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@ResponseBody
public class HelloController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/hello")
    public String hello(){
        String ip=request.getHeader("X-Real-Ip");
        System.out.println(ip);
        return "小吉力为你守门!!!您所在的ip为:"+ip;
    }
}
