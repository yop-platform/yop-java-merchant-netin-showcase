/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * title: 静态资源访问控制器<br>
 * description: 提供js/css等静态资源访问，无需登录<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Controller
public class StaticResourceController {
    @GetMapping({"/js/{file:.+}", "/static/js/{file:.+}"})
    @ResponseBody
    public ResponseEntity<Resource> getJs(@PathVariable String file) throws IOException {
        Resource resource = new ClassPathResource("static/js/" + file);
        if (!resource.exists()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.valueOf("application/javascript")).body(resource);
    }
    @GetMapping({"/css/{file:.+}", "/static/css/{file:.+}"})
    @ResponseBody
    public ResponseEntity<Resource> getCss(@PathVariable String file) throws IOException {
        Resource resource = new ClassPathResource("static/css/" + file);
        if (!resource.exists()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.valueOf("text/css")).body(resource);
    }
} 