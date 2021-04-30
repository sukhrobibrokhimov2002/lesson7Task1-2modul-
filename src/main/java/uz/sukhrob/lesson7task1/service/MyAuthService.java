package uz.sukhrob.lesson7task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.sukhrob.lesson7task1.entity.Role;
import uz.sukhrob.lesson7task1.entity.User;
import uz.sukhrob.lesson7task1.entity.component.Constants;
import uz.sukhrob.lesson7task1.entity.component.MailSender;
import uz.sukhrob.lesson7task1.payload.ApiResponse;
import uz.sukhrob.lesson7task1.payload.RegisterUserDto;
import uz.sukhrob.lesson7task1.repository.RoleRepository;
import uz.sukhrob.lesson7task1.repository.UserRepository;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class MyAuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        return byUsername.orElse(null);
    }

    public ApiResponse register(RegisterUserDto registerUserDto) throws MessagingException {
        boolean existsByUsername = userRepository.existsByUsername(registerUserDto.getUsername());
        if (existsByUsername) return new ApiResponse("This username already exists", false);

        String password = registerUserDto.getPassword();
        String confirmPassword = registerUserDto.getConfirmPassword();
        if (!confirmPassword.equals(password)) return new ApiResponse("Parollar mos emas", false);
        Optional<Role> optionalRole = roleRepository.findByName(Constants.USER);
        if (!optionalRole.isPresent()) return new ApiResponse("Xatolik", false);

        User user = new User();
        user.setRole(optionalRole.get());
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setFullName(registerUserDto.getFullName());
        userRepository.save(user);
        boolean mailTextAdd = mailSender.mailTextAdd(registerUserDto.getUsername());
        if (mailTextAdd) {
            return new ApiResponse("The confirmation messsage has been sent to your email, please confirm your account", true);
        }
        return new ApiResponse("Error in sending email to your account", false);

    }


    public ApiResponse verifyEmail(String email) {
        Optional<User> byUsername = userRepository.findByUsername(email);
        User user = byUsername.get();
        user.setEnabled(true);
        userRepository.save(user);
        return new ApiResponse("Account has been verified, Welcome!!!!", true);
    }
}
