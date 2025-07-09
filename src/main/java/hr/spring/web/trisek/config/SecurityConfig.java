package hr.spring.web.trisek.config;

import hr.spring.web.trisek.filter.JwtAuthFilter;
import hr.spring.web.trisek.security.CustomLoginSuccessHandler;
import hr.spring.web.trisek.service.MyUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    private JwtAuthFilter jwtAuthFilter;
    private final MyUserDetailService userDetailsService;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/login").anonymous()
                        .requestMatchers("/rest/**").authenticated()
                        .requestMatchers("/auth/api/v1/login", "/auth/api/v1/logout", "/auth/api/v1/refreshToken").permitAll()
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
                        .successHandler(customLoginSuccessHandler)
                        .permitAll()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutSuccessUrl("/items")
                        .permitAll()
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}