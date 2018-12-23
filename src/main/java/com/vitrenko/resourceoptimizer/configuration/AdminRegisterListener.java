package com.vitrenko.resourceoptimizer.configuration;

import com.google.common.collect.Lists;
import com.vitrenko.resourceoptimizer.domain.*;
import com.vitrenko.resourceoptimizer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminRegisterListener {

    private final UserService userService;

    @EventListener
    public void registerAdmin(ContextRefreshedEvent contextRefreshedEvent) {
        UserForm userForm = new UserForm().setEmail("something@gmail.com")
                                          .setFirstName("Some")
                                          .setLastName("Thing")
                                          .setNewPassword("123456");
        AppUser appUser = userService.register(userForm);
        Task task = new Task().setName("First Task")
                              .setResources(1)
                              .setAggregates(Lists.newArrayList(new Aggregate().setName("first")
                                                                               .setEffectParameters(new EffectParameters().setAlpha(0.5)
                                                                                                                                 .setWeight(0.5)
                                                                                                                                 .setMaxResources(1)
                                                                                                                                 .setMinResources(1))));
        userService.saveTaskToUser(task, appUser.getEmail());

        log.error(userService.findByEmail("something@gmail.com").toString());
    }
}
