package com.jili.testnatapp.controller;

import com.jili.testnatapp.util.SecurityUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/api/weixin")
@Api(tags = "微信 - 接口")
public class WeixinIndexController {

    // TODO 这里的token是微信公众平台上自己所配的！
    private static final String token = "jili";

    /**
     * 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数如下表所示:
     * signature: 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * timestamp 时间戳
     * nonce: 随机数
     * echostr: 随机字符串
     */

    /**
     * 官方文档说明：
     * 开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。加密/校验流程如下：
     * 1.将token、timestamp、nonce三个参数进行字典序排序;
     * 2.将三个参数字符串拼接成一个字符串进行sha1加密;
     * 3.开发者获得加密后的字符串可与signature对比，标识该请求来源于微信;
     */


    @GetMapping("/checkSignature")
    public void checkSignature(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("============= 处理微信认证 ===============");

        // 拿到微信的请求参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        // 1.将token、timestamp、nonce三个参数进行字典序排序 b a d c h ==>a b c d h
        String[] strArr = {token, timestamp, nonce};
        // 字典排序
        Arrays.sort(strArr);
        System.out.println(strArr.toString());

        //2.拼接排序后的token、timestamp、nonce，进行sha1加密
        StringBuffer sBuffer = new StringBuffer();

        for (String str : strArr) {
            sBuffer.append(str);
        }
        System.out.println(sBuffer);
        //sha1加密
        String sha1Str= SecurityUtil.sha1(sBuffer.toString());
        //3.把加密后的字符串与微信signature对比
        if(sha1Str.equals(signature)){
            response.getWriter().println(echostr);
        }
    }
}
