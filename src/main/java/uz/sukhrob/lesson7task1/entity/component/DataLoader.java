package uz.sukhrob.lesson7task1.entity.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.sukhrob.lesson7task1.entity.Role;
import uz.sukhrob.lesson7task1.entity.User;
import uz.sukhrob.lesson7task1.entity.enums.Permissions;
import uz.sukhrob.lesson7task1.repository.RoleRepository;
import uz.sukhrob.lesson7task1.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static uz.sukhrob.lesson7task1.entity.enums.Permissions.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            Role adminRole = new Role();
            Permissions[] values = Permissions.values();
            adminRole.setName(Constants.ADMIN);
            adminRole.setPermissions(Arrays.asList(values));
            adminRole.setDescription("Sistema egasi");
            Role savedAdmin = roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName(Constants.USER);
            userRole.setPermissions(Arrays.asList(ADD_COMMENT, EDIT_COMMENT, DELETE_MY_COMMENT,DELETE_MY_ACCOUNT));
            userRole.setDescription("Sistema egasi");
            Role savedUser = roleRepository.save(userRole);


            User admin = new User();
            admin.setEnabled(true);
            admin.setUsername("admin");
            admin.setFullName("ADmincha");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(savedAdmin);
            userRepository.save(admin);

            User user = new User();
            user.setEnabled(true);
            user.setUsername("user");
            user.setFullName("Usercha");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(savedUser);
            userRepository.save(user);
        }


    }
}
