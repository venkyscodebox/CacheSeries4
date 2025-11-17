package org.main.cacheseries4.controller;

import org.main.cacheseries4.entity.User;
import org.main.cacheseries4.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getuser/{email}")
    public User getUser(@PathVariable("email") String email){
        System.out.println("Email:"+email);
        return userService.findByEmail(email);
    }

    @PostMapping("/update-email")
    public void updateEmail(@RequestParam String oldEmail, @RequestParam String newEmail){
        userService.updateEmail(oldEmail, newEmail);
    }
}
