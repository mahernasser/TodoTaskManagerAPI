package com.maher.controllers.home;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @RequestMapping(value = "/")
    public String greeting() {
        return "Hello SpringBoot!!";
    }

    @GetMapping("/{name}")
    public String greetingWithName(@PathVariable String name) {
        return "Hi "+name.toUpperCase();
    }


}
