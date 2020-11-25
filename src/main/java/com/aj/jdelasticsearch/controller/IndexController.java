package com.aj.jdelasticsearch.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author  aj
 * 登录首页
*/
@Controller
public class IndexController {
    /**
     *知识点，可以通过大括号来实现多索引指向
    */
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
