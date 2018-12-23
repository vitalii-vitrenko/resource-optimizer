package com.vitrenko.resourceoptimizer.service;

import com.vitrenko.resourceoptimizer.domain.AppUser;
import com.vitrenko.resourceoptimizer.domain.Task;
import com.vitrenko.resourceoptimizer.domain.UserForm;
import com.vitrenko.resourceoptimizer.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void update(@NonNull UserForm userForm) {
        AppUser user = findUserOrThrow(userForm.getEmail());
        if (isNotBlank(userForm.getNewPassword())) {
            user.setPassword(passwordEncoder.encode(userForm.getNewPassword()));
        }
        user.setFirstName(defaultIfBlank(userForm.getFirstName(), user.getFirstName()));
        user.setLastName(defaultIfBlank(userForm.getLastName(), user.getLastName()));

        userRepository.save(user);
    }

    public void deleteByEmail(@NonNull String email) {
        userRepository.deleteByEmail(email);
    }

    public void saveTaskToUser(@NotNull Task task, @NonNull String userEmail) {
        AppUser user = findUserOrThrow(userEmail);
        user.getTasks().add(task);
        userRepository.save(user);
    }

    public boolean deleteUserTask(@NotNull String name, @NonNull String userEmail) {
        AppUser user = findUserOrThrow(userEmail);
        boolean removed = user.getTasks().removeIf(task -> task.getName().equals(name));
        userRepository.save(user);
        return removed;
    }

    private AppUser findUserOrThrow(@NonNull String userEmail) {
        AppUser user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new IllegalArgumentException(format("User with email%sdoes not exist", userEmail));
        }
        return user;
    }

    public AppUser register(@NonNull UserForm userForm) {
        if (userRepository.findByEmail(userForm.getEmail()) != null) {
            throw new EmailAlreadyExistException(userForm.getEmail());
        }
        AppUser newUser = new AppUser()
                .setPassword(passwordEncoder.encode(userForm.getNewPassword()))
                .setEmail(userForm.getEmail())
                .setFirstName(userForm.getFirstName())
                .setLastName(userForm.getLastName());
        return userRepository.save(newUser);
    }

    public List<AppUser> findAllUsers() {
        return userRepository.findAll();
    }

    public AppUser findByEmail(@NonNull String email) {
        if (isNotBlank(email)) {
            return userRepository.findByEmail(email);
        }
        return null;
    }
}
