package tech.challenge.notification.system.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AccessTokenFilter accessTokenFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(AccessTokenFilter accessTokenFilter, UserDetailsService userDetailsService) {
        this.accessTokenFilter = accessTokenFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(req -> {

                // ENDPOINTS PÚBLICOS
                req.requestMatchers("/auth/login", "/auth").permitAll();
                req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**",
                                "/swagger-resources/**", "/webjars/**").permitAll();

                // CRIAÇÃO DE USUÁRIOS (público)
                req.requestMatchers(HttpMethod.POST, "/teacher").permitAll();
                req.requestMatchers(HttpMethod.POST, "/admin").permitAll();
                req.requestMatchers(HttpMethod.POST, "/student").permitAll();

                // AUTH - atualizar perfil (requer autenticação)
                req.requestMatchers(HttpMethod.POST, "/auth/update-profile").hasAnyRole("TEACHER", "STUDENT", "ADMIN");

                // TEACHER
                req.requestMatchers(HttpMethod.GET, "/teacher").hasAnyRole("TEACHER", "STUDENT", "ADMIN");
                req.requestMatchers(HttpMethod.GET, "/teacher/**").hasAnyRole("TEACHER", "ADMIN");
                req.requestMatchers(HttpMethod.DELETE, "/teacher/**").hasRole("ADMIN");

                // ADMIN
                req.requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN");
                req.requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN");

                // STUDENT
                req.requestMatchers(HttpMethod.GET, "/student/**").hasRole("ADMIN");
                req.requestMatchers(HttpMethod.DELETE, "/student/**").hasRole("ADMIN");

                // LESSONS - regras específicas primeiro (com /feedbacks)
                req.requestMatchers(HttpMethod.POST, "/lessons/*/feedbacks").hasAnyRole("STUDENT", "ADMIN");
                req.requestMatchers(HttpMethod.GET, "/lessons/*/feedbacks").permitAll();
                
                // LESSONS - gerais
                req.requestMatchers(HttpMethod.GET, "/lessons/*").permitAll();
                req.requestMatchers(HttpMethod.POST, "/lessons").hasAnyRole("TEACHER", "ADMIN");
                req.requestMatchers(HttpMethod.PUT, "/lessons/**").hasAnyRole("TEACHER", "ADMIN");
                req.requestMatchers(HttpMethod.DELETE, "/lessons/**").hasAnyRole("TEACHER", "ADMIN");

                // QUALQUER OUTRO REQUER AUTENTICAÇÃO
                req.anyRequest().authenticated();
            })
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(csrf -> csrf.disable())
            .addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
