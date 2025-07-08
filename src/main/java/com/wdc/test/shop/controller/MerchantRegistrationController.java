package com.wdc.test.shop.controller;

import com.wdc.test.shop.model.MerchantRegistration;
import com.wdc.test.shop.model.User;
import com.wdc.test.shop.service.MerchantRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户入网控制器
 */
@Controller
@RequestMapping("/merchant")
public class MerchantRegistrationController {
    
    private static final Logger logger = LoggerFactory.getLogger(MerchantRegistrationController.class);
    
    private final MerchantRegistrationService merchantRegistrationService;
    
    @Autowired
    public MerchantRegistrationController(MerchantRegistrationService merchantRegistrationService) {
        this.merchantRegistrationService = merchantRegistrationService;
    }
    
    /**
     * 商户入网列表页
     */
    @GetMapping("/register/list")
    public String merchantRegistrationList(Model model, HttpSession session) {
        // 检查是否登录
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<MerchantRegistration> registrations = merchantRegistrationService.getAllMerchantRegistrations();
        model.addAttribute("registrations", registrations);
        
        return "payment/merchant-register-list";
    }
    
    /**
     * 商户入网申请页
     */
    @GetMapping("/register/apply")
    public String merchantRegistrationApply(Model model, HttpSession session) {
        // 检查是否登录
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("registration", new MerchantRegistration());
        return "payment/merchant-register-apply";
    }
    
    /**
     * 商户入网详情页
     */
    @GetMapping("/register/{id}")
    public String merchantRegistrationDetail(@PathVariable Long id, Model model, HttpSession session) {
        // 检查是否登录
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        MerchantRegistration registration = merchantRegistrationService.getMerchantRegistrationById(id);
        if (registration == null) {
            return "redirect:/merchant/register/list";
        }
        
        model.addAttribute("registration", registration);
        return "payment/merchant-register-detail";
    }
    
    /**
     * 上传商户资质文件
     */
    @PostMapping("/register/upload")
    @ResponseBody
    public Map<String, Object> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileType") String fileType,
            HttpSession session) {
        
        Map<String, Object> result = new HashMap<>();
        
        // 检查是否登录
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("message", "请先登录");
            return result;
        }
        
        // 上传文件
        MerchantRegistrationService.UploadResult uploadResult = merchantRegistrationService.uploadMerchantFile(file, fileType);
        
        result.put("success", uploadResult.isSuccess());
        result.put("message", uploadResult.getMessage());
        result.put("fileUrl", uploadResult.getFileUrl());
        
        return result;
    }
    
    /**
     * 提交商户入网申请
     */
    @PostMapping("/register/submit")
    public String submitRegistration(MerchantRegistration registration, Model model, HttpSession session) {
        // 检查是否登录
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        // 提交商户入网申请
        MerchantRegistrationService.MerchantRegisterResult result = merchantRegistrationService.registerMerchant(registration);
        
        if (result.isSuccess()) {
            model.addAttribute("message", result.getMessage());
            return "redirect:/merchant/register/" + result.getRegistration().getId();
        } else {
            model.addAttribute("error", result.getMessage());
            model.addAttribute("registration", registration);
            return "payment/merchant-register-apply";
        }
    }
    
    /**
     * 处理商户入网回调
     */
    @PostMapping("/register/callback")
    @ResponseBody
    public String registerCallback(HttpServletRequest request) {
        logger.info("接收到商户入网回调");
        
        try {
            // 获取所有回调参数
            Map<String, String> params = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                params.put(paramName, paramValue);
            }
            
            // 处理回调
            boolean result = merchantRegistrationService.processRegisterCallback(params);
            
            if (result) {
                logger.info("商户入网回调处理成功");
                return "SUCCESS";
            } else {
                logger.error("商户入网回调处理失败");
                return "FAIL";
            }
        } catch (Exception e) {
            logger.error("处理商户入网回调异常", e);
            return "FAIL";
        }
    }
} 