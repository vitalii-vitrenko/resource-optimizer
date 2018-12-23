package com.vitrenko.resourceoptimizer.controller;

import com.google.common.collect.ImmutableMap;
import com.vitrenko.resourceoptimizer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin/users")
    public ModelAndView findAllUsers() {
        return new ModelAndView("userList", ImmutableMap.of("userList", userService.findAllUsers()));
    }

    @DeleteMapping("/admin/users/{email}")
    public ModelAndView deleteUser(@PathVariable String email) {
        if (userService.findByEmail(email) == null) {
            throw new ResourceNotFoundException();
        }
        userService.deleteByEmail(email);
        return new ModelAndView("users");
    }
}
