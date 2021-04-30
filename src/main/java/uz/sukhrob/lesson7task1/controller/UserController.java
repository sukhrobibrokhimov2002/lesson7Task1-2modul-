package uz.sukhrob.lesson7task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sukhrob.lesson7task1.entity.User;
import uz.sukhrob.lesson7task1.payload.AddUserDto;
import uz.sukhrob.lesson7task1.payload.ApiResponse;
import uz.sukhrob.lesson7task1.service.UserService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize(value = "hasAnyAuthority(ADD_USER)")
    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody AddUserDto addUserDto) throws MessagingException {
        ApiResponse apiResponse = userService.addUser(addUserDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority(DELETE_USER)")
    @DeleteMapping("/deleteMyAccount")
    public ResponseEntity<?> deleteMyAccount() {
        ApiResponse apiResponse = userService.deleteMyAccount();
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }
    @PreAuthorize(value = "hasAnyAuthority(DELETE_MY_ACCOUNT)")
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        ApiResponse apiResponse = userService.deleteUser(username);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority(EDIT_USER)")
    @PatchMapping("/editUser")
    public ResponseEntity<?> editUser(@RequestParam String username, @Valid @RequestBody AddUserDto addUserDto) {
        ApiResponse apiResponse = userService.editUser(username, addUserDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }
    @PreAuthorize(value = "hasAnyAuthority(VIEW_USER)")
    @GetMapping
    public ResponseEntity<?> getAll() {
        Page<User> users = userService.viewUser();
        if (users.isEmpty()) return ResponseEntity.status(409).body(users);
        return ResponseEntity.ok(users);
    }

}
