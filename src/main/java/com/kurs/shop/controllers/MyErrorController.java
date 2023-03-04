package com.kurs.shop.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController
{
    @RequestMapping("/error")
    public String error()
    {
        return "error";
    }
}
