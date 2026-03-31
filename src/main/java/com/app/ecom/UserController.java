package com.app.ecom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(),
                HttpStatus.OK);
        //return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user = userService.fetchUser(id);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully");
    }
}