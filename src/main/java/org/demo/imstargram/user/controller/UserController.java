package org.demo.imstargram.user.controller;

import org.demo.imstargram.user.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController{
    @GetMapping("/login")
    public String goLoginPage() {
        return "login";
    }

   @PostMapping("/login")
   public String login(Model model, User loginUser){
        System.out.println("\n 로그인 ID : "+loginUser.getId());
        System.out.println(" 로그인 PW : "+loginUser.getPassword());
        model.addAttribute("user",loginUser);
        return "main";
   }

   @GetMapping("/main")
   public String main(Model model, User user) {
        model.addAttribute("user", user);
        return "main";
   }

    @GetMapping("/register")
    public String goRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register( User newUser) {
        System.out.println("\n 가입 ACCOUNT : "+ newUser.getAccount());
        System.out.println(" 가입 NAME : "+ newUser.getName());
        System.out.println(" 가입 ID : "+ newUser.getId());
        System.out.println(" 가입 PASSWORD : "+ newUser.getPassword());

        return "success";
    }

    @PostMapping("/sample")
    public String postSample(Model model, String param1) {
        System.out.println(param1);
        return "sample/sample";
    }
    @GetMapping("/sample")
    public String getSample() {
        return "sample/sample";
    }


}
