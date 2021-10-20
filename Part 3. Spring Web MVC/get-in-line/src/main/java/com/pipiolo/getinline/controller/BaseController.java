package com.pipiolo.getinline.controller;

import com.pipiolo.getinline.exception.GeneralException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

}
