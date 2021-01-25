package com.bookstore.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bookstore.entity.Info;
import com.bookstore.service.UserService;
import com.bookstore.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaojun
 * @create 2021-01-25 17:03
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;
    @GetMapping("/mytest")
    public String mytest(){
        return "fasdfasd";
    }

    @GetMapping("/loginIn")
    public Map<String, Object> loginIn(Info info){
        log.info("用户名：loginIn");
        log.info("用户名：[{}]", info.getName());
        log.info("密码：[{}]", info.getPwd());

        Map<String, Object> map = new HashMap<>();
        try{
            List<Info> infoDB = userService.findByName(info.getName());
            /*生成JWT令牌*/

            Map<String, String> payload = new HashMap<>();
            boolean state = false;
            for (Info info_ : infoDB) {
                if(info_.getPwd()==info.getPwd()){
                    state = true;
                    info = info_;
                    break;
                }
            }
            if(state){
                payload.put("id", String.valueOf(info.getId()));
                payload.put("name", info.getName());
                payload.put("pwd", String.valueOf(info.getPwd()));
                String token = JWTUtils.getToken(payload);
                map.put("state",true);
                map.put("msg","认证成功");
                map.put("token", token);
                return map;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("state",false);
        map.put("msg","认证失败");
        return map;
    }
    /**
     *@description:其他页面访问该系统时，需要携带token才能访问。
     *@param
     *@return:
     *@author:  Yao
     */
    @GetMapping("/user/test")
    public Map<String, Object> test(String token){
        Map<String, Object> map = new HashMap<>();
        if(token ==null){
            map.put("state",false);
            map.put("msg","token为空");
            return map;
        }
        log.info("当前的token为：[{}]", token);
        try {
            DecodedJWT decodedJWT = JWTUtils.verifyToken(token);
            map.put("state",true);
            map.put("msg","请求成功");
            return map;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg","签名错误");
        }catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg","令牌过期");
        }catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("msg","加解密算法不匹配");
        }catch (Exception e) {
            e.printStackTrace();
            map.put("msg","token无效");
        }
        map.put("state",false);
        return map;
    }
}
