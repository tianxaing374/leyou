package com.leyou.page.web;

import com.leyou.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String toHello(Model model){
        model.addAttribute("msg","hello,thymeleaf!");
        User user1 = new User("aa",12);
        User user2 = new User("bb",23);
        model.addAttribute("users", Arrays.asList(user1,user2));
        return "Hello"; //普通字符串被当成视图名称，结合前缀和后缀
    }

}
