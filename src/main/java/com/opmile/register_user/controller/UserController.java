package com.opmile.register_user.controller;

import com.opmile.register_user.dto.user.UserCreateDTO;
import com.opmile.register_user.dto.user.UserDTO;
import com.opmile.register_user.dto.user.UserUpdateDTO;
import com.opmile.register_user.model.User;
import com.opmile.register_user.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUser().stream()
                .map(UserDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return new UserDTO(userService.getUser(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserCreateDTO dto) {
        User user = userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }

    @PostMapping("/fetch-external")
    public ResponseEntity<List<UserDTO>> registerFetchExternal(@RequestParam(required = true) int quantity) {
        List<User> users = userService.registerFetch(quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                users.stream()
                        .map(UserDTO::new)
                        .toList()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestBody UserUpdateDTO dto) {
        User updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(new UserDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        userService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
