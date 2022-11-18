package com.dongnebook.global.config.security.auth.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2TestController {
    @GetMapping("/hello-oauth2")
    public String home() {
        return "OAuth2test"; //뷰 이름 리턴
    }
}
