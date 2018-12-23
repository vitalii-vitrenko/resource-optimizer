package com.vitrenko.resourceoptimizer.controller;

import com.vitrenko.resourceoptimizer.domain.AppUser;
import com.vitrenko.resourceoptimizer.domain.Solution;
import com.vitrenko.resourceoptimizer.domain.Task;
import com.vitrenko.resourceoptimizer.service.UserService;
import com.vitrenko.resourceoptimizer.solver.TaskSolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskSolver taskSolver;
    private final UserService userService;

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAll(@AuthenticationPrincipal User user) {
        List<Task> tasks = userService.findByEmail(user.getUsername()).getTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/{email:.+}/{name}")
    public ResponseEntity<Task> getByName(@PathVariable String name, @AuthenticationPrincipal User user) {
        AppUser appUser = userService.findByEmail(user.getUsername());
        Optional<Task> task = appUser.getTasks().stream().filter(t -> t.getName().equals(name)).findFirst();
        return ResponseEntity.of(task);
    }

    @DeleteMapping("/tasks/{email:.+}/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name, @AuthenticationPrincipal User user) {
        boolean removed = userService.deleteUserTask(name, user.getUsername());
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping({"/tasks/solve", "/tasks/{email:.+}/{name}/save"})
    public ResponseEntity<Solution> solve(@Valid Task task) {
        return ResponseEntity.ok(taskSolver.solve(task));
    }

    @PostMapping("/tasks/{email:.+}/{name}/save")
    public ResponseEntity<?> save(Task task, @AuthenticationPrincipal User user) {
        userService.saveTaskToUser(task, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
