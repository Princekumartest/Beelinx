package com.beelinx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class oauth2Controller {
    @GetMapping("/home")
    public static String home(){
        return "wlc to oauth2";
    }


}
