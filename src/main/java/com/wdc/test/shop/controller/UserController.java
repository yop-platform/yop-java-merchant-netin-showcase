package com.wdc.test.shop.controller;

import com.wdc.test.shop.model.User;
import com.wdc.test.shop.service.UserService;
import com.wdc.test.shop.payment.yeepay.WechatAuthApplyRequest;
import com.wdc.test.shop.payment.yeepay.WechatAuthApplyResponse;
import com.wdc.test.shop.payment.yeepay.YeepayChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    
    private final UserService userService;
    private final YeepayChannel yeepayChannel;
    
    @Autowired
    public UserController(UserService userService, YeepayChannel yeepayChannel) {
        this.userService = userService;
        this.yeepayChannel = yeepayChannel;
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, 
                       HttpSession session, Model model) {
        User user = userService.login(username, password);
        if (user == null) {
            model.addAttribute("error", "用户名或密码错误");
            return "user/login";
        }
        
        session.setAttribute("user", user);
        return "redirect:/products";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/user/wechat-auth-apply")
    public String wechatAuthApplyPage() {
        return "user/wechat_auth_apply";
    }

    @PostMapping("/user/wechat-auth-apply")
    public String wechatAuthApplySubmit(WechatAuthApplyRequest req, Model model) {
        try {
            WechatAuthApplyResponse resp = yeepayChannel.wechatAuthApply(req);
            if (resp != null && resp.isSuccess()) {
                model.addAttribute("success", "申请成功，申请单编号：" + resp.getApplymentId());
            } else {
                model.addAttribute("error", resp != null ? resp.getReturnMsg() : "申请失败");
            }
        } catch (Exception e) {
            model.addAttribute("error", "系统异常: " + e.getMessage());
        }
        return "user/wechat_auth_apply";
    }
}