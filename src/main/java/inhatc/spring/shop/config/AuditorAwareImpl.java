package inhatc.spring.shop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";

//        Optional<String> string = SecurityContextHolder.getContext()
//                .getAuthentication().getName();
        if (authentication != null) {
            userId = authentication.getName();
        }

        return Optional.of(userId);
    }
}
