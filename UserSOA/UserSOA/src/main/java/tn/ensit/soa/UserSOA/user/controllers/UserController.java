package tn.ensit.soa.UserSOA.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.ensit.soa.UserSOA.user.dto.UserDto;
import tn.ensit.soa.UserSOA.user.entities.User;
import tn.ensit.soa.UserSOA.user.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = service.getAllUsers().stream()
                .map(user -> new UserDto(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getOneUser(@PathVariable Long id) {
        return service.getOneUser(id)
                .map(user -> new UserDto(user.getId(), user.getUsername()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public UserDto createUser(@RequestBody User user) {
        User createdUser = service.createUser(user);
        return new UserDto(createdUser.getId(), createdUser.getUsername());
    }
}