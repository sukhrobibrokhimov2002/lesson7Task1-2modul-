package uz.sukhrob.lesson7task1.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.sukhrob.lesson7task1.entity.Role;
import uz.sukhrob.lesson7task1.entity.User;
import uz.sukhrob.lesson7task1.entity.component.MailSender;
import uz.sukhrob.lesson7task1.payload.AddUserDto;
import uz.sukhrob.lesson7task1.payload.ApiResponse;

import uz.sukhrob.lesson7task1.repository.RoleRepository;
import uz.sukhrob.lesson7task1.repository.UserRepository;

import javax.mail.MessagingException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailSender mailSender;

    public ApiResponse addUser(AddUserDto addUserDto) throws MessagingException {
        boolean existsByUsername = userRepository.existsByUsername(addUserDto.getUsername());
        if (existsByUsername) return new ApiResponse("Username already exist", false);

        Optional<Role> optionalRole = roleRepository.findById(addUserDto.getRoleId());
        if (!optionalRole.isPresent()) return new ApiResponse("Role not found", false);
        Role role = optionalRole.get();

        User user = new User();
        user.setUsername(addUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(addUserDto.getPassword()));
        user.setRole(role);
        user.setFullName(addUserDto.getFullName());
        userRepository.save(user);

        boolean mailTextAdd = mailSender.mailTextAdd(addUserDto.getUsername());
        if (mailTextAdd)
            return new ApiResponse("Confirmation email sent to your email,Please confirm your account", true);
        return new ApiResponse("Error in sending confirmation email", false);

    }

    public ApiResponse deleteMyAccount() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userRepository.deleteById(user.getId());
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }

    public ApiResponse deleteUser(String username) {
        try {
            Optional<User> byUsername = userRepository.findByUsername(username);
            if (!byUsername.isPresent()) return new ApiResponse("User not found", false);
            User user = byUsername.get();
            userRepository.deleteById(user.getId());
            return new ApiResponse("Succesfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }

    }

    public ApiResponse editUser(String username, AddUserDto addUserDto) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent()) return new ApiResponse("User not found", false);
        Optional<Role> roleRepositoryById = roleRepository.findById(addUserDto.getRoleId());
        if (!roleRepositoryById.isPresent()) return new ApiResponse("Role not found", false);

        User user = byUsername.get();
        boolean existsByUsernameAndIdNot = userRepository.existsByUsernameAndIdNot(addUserDto.getUsername(), user.getId());
        if (existsByUsernameAndIdNot) return new ApiResponse("username already exists", false);
        user.setFullName(addUserDto.getFullName());
        user.setPassword(passwordEncoder.encode(addUserDto.getPassword()));
        user.setRole(roleRepositoryById.get());
        user.setUsername(addUserDto.getUsername());
        userRepository.save(user);
        return new ApiResponse("User successfully edited", true);


    }

    public Page<User> viewUser() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> all = userRepository.findAll(pageable);
        return all;
    }

}
