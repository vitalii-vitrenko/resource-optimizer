package com.vitrenko.resourceoptimizer.controller;

import com.vitrenko.resourceoptimizer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public ModelAndView homePage(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView("home");
        if (user != null) {
            modelAndView.addObject("appUesr", userService.findByEmail(user.getUsername()));
        }
        return modelAndView;
    }
}
