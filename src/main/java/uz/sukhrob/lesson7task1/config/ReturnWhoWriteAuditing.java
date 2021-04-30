package uz.sukhrob.lesson7task1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uz.sukhrob.lesson7task1.entity.User;

@Configuration
@EnableJpaAuditing
public class ReturnWhoWriteAuditing {

    @Bean
    AuditorAware<User> auditorAware() {

        return new KnowingWhoCrOrUpdAuditing();
    }


}
