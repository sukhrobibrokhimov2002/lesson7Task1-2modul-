package uz.sukhrob.lesson7task1.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.sukhrob.lesson7task1.entity.User;

import java.util.Optional;

public class KnowingWhoCrOrUpdAuditing implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
                authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
