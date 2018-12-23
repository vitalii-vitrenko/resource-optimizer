package com.vitrenko.resourceoptimizer.service;

import com.vitrenko.resourceoptimizer.domain.AppUser;
import com.vitrenko.resourceoptimizer.domain.UserRole;
import com.vitrenko.resourceoptimizer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(format("AppUser with email %s was not found", username));
        }
        return toUserDetails(user);
    }

    private UserDetails toUserDetails(AppUser user) {
        return User.withUsername(user.getEmail())
                   .password(user.getPassword())
                   .roles(user.getUserRoles().stream().map(UserRole::toString).toArray(String[]::new))
                   .build();
    }
}
