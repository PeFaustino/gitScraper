package com.backend.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitController {

    @RequestMapping("/")
    public String index() {
        return "redirect:swagger-ui.html";
    }
}
