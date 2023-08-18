package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.config;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.AdminServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {
    private AdminServiceImp adminServiceImp;

    public AdminSecurityConfig(AdminServiceImp adminServiceImp) {
        this.adminServiceImp = adminServiceImp;
    }

    public UserDetailsService getUserDetailsService() {
        return adminServiceImp;
    }

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        http
                .antMatcher("/admin/**")
                .userDetailsService(adminServiceImp)
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().hasAuthority("ROLE_ADMIN")
                )

                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/admin/new")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return this.adminServiceImp;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
