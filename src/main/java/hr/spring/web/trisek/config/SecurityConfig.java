package hr.spring.web.trisek.config;

import hr.spring.web.trisek.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/cart/checkout").hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers("/orders/history").hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers("/items", "/cart/**").permitAll()
                        .requestMatchers("/admin/**").hasRole(ROLE_ADMIN)
                        .requestMatchers("/category/**").hasRole(ROLE_ADMIN)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/items", false)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/items")
                        .permitAll()
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }

}
