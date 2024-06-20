package org.shane.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{name}")
    public String getUser(@PathVariable String name) {
        return "user" + name;
    }
}
