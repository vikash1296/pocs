package com.inventoryservice.controller;

import com.inventoryservice.entity.Users;
import com.inventoryservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/insertUser")
    public ResponseEntity<String> addNewUserInfo(@RequestBody @Validated Users users) {
        Users insertedNewUserInfo = userService.addNewUserInfo(users);
        if (insertedNewUserInfo != null) {
            return new ResponseEntity<>("User added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong while adding user info", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/fetchUser/{id}")
    public ResponseEntity<Users> fetchUserById(@PathVariable Long id) {
        Optional<Users> userInfoById = userService.findUserById(id);
        return userInfoById.map(users -> new ResponseEntity<>(users, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fetchAllUsers")
    public ResponseEntity<List<Users>> fetchAllUsers() {
        List<Users> allUsers = userService.findAllUsers();
        if (allUsers != null) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/updateUserInfo/{id}")
    public ResponseEntity<String> updateUserInfo(@PathVariable Long id, @RequestBody Users users) {
        Users updatedUserInfo = userService.updateUserInfo(id, users);
        if (updatedUserInfo != null) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong while updating user info", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('admin')")
    @PatchMapping("/updateUserSpecificInfo/{id}")
    public ResponseEntity<String> updateUserSpecificInfo(@PathVariable Long id,
                                                         @RequestBody Map<String, Object> userSpecificInfo) {
        Users updatedUserSpecificInfo = userService.updateUserSpecificInfo(id, userSpecificInfo);
        if (updatedUserSpecificInfo != null) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong while updating user info", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/removeUserInfo/{id}")
    public ResponseEntity<String> removeUserInfo(@PathVariable Long id) {
        if (userService.findUserById(id).isPresent()) {
            userService.deleteUserInfoByID(id);
            return new ResponseEntity<>("User removed successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong while removing user info", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/removeAllUsers")
    public ResponseEntity<String> removeAllUsers() {
        userService.deleteAllUsers();
        if (userService.findAllUsers() == null) {
            return new ResponseEntity<>("Something went wrong while deleting all users", HttpStatus.OK);
        }
        return new ResponseEntity<>("Removed all users", HttpStatus.OK);
    }
}
