package com.ms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class teat {

    @RequestMapping("/sayHi")
    public String madin(){
        return "sayHi";
    }
}
