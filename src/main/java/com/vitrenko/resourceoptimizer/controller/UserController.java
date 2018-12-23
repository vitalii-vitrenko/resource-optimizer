package com.vitrenko.resourceoptimizer.controller;

import com.google.common.collect.ImmutableMap;
import com.vitrenko.resourceoptimizer.domain.AppUser;
import com.vitrenko.resourceoptimizer.domain.UserForm;
import com.vitrenko.resourceoptimizer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

import static java.lang.String.format;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final String USER_CONTROL_AUTHORIZE_EXPRESSION
            = "#userForm.email == authentication.principal.username || hasRole('ADMINISTRATOR')";

    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @PostMapping("/registration")
    public ModelAndView register(@RequestParam @Valid UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("registerError", result.getModel());
        }
        userService.register(userForm);
        return new ModelAndView("registerSuccess");
    }

    @GetMapping("/users/{email:.+}")
    @PreAuthorize("#email == authentication.principal.username || hasRole('ADMINISTRATOR')")
    public ModelAndView userInfo(@PathVariable String email) {
        AppUser appUser = userService.findByEmail(email);
        if (appUser == null) {
            throw new ResourceNotFoundException();
        }
        return new ModelAndView("userInfo", ImmutableMap.of("userInfo", appUser));
    }

    @PostMapping("/users/{email:.+}")
    @PreAuthorize("#userForm.email == authentication.principal.username || hasRole('ADMINISTRATOR')")
    public ModelAndView updateUserInfo(@Valid @RequestParam UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("userUpdateError", result.getModel());
        }
        AppUser appUser = userService.findByEmail(userForm.getEmail());
        if (appUser == null) {
            throw new ResourceNotFoundException();
        }
        userService.update(userForm);
        return new ModelAndView(new RedirectView(format("/users/%s", userForm.getEmail())));
    }
}
