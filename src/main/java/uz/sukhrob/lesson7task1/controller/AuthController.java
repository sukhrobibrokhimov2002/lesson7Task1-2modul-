package uz.sukhrob.lesson7task1.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.sukhrob.lesson7task1.entity.User;
import uz.sukhrob.lesson7task1.payload.ApiResponse;
import uz.sukhrob.lesson7task1.payload.LoginDto;
import uz.sukhrob.lesson7task1.payload.RegisterUserDto;
import uz.sukhrob.lesson7task1.security.JwtProvider;
import uz.sukhrob.lesson7task1.service.MyAuthService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    MyAuthService myAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(user.getUsername(), user.getRole());

            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(409).body("Username yoki parol xato");
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) throws MessagingException {
        ApiResponse register = myAuthService.register(registerUserDto);
        if (register.isSuccess()) return ResponseEntity.ok(register);
        return ResponseEntity.status(409).body(null);
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestParam String email)   {
        ApiResponse apiResponse = myAuthService.verifyEmail(email);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }
}
