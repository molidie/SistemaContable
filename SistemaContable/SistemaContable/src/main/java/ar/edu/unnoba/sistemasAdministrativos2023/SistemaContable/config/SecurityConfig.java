package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.config;


import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.AdminServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    private AdminServiceImp userDetailsService;

    @Autowired
    public SecurityConfig(AdminServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        http
        
                .antMatcher("/admin/**")
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/webjars/**", "/resources/**","/css/**").permitAll()
                        .anyRequest().hasAuthority("ROLE_ADMIN"))

                .formLogin((form) -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/home")
                        .permitAll())

               
                
                .logout((logout) -> logout
                .logoutUrl("/admin/home")
                .logoutSuccessUrl("/admin/login")
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return this.userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
