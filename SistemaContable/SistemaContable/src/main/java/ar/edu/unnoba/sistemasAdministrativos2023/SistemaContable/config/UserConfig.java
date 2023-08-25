package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.config;

import ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class UserConfig {

    private UserServiceImp userDetailsService;

    @Autowired
    public UserConfig(UserServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserServiceImp getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .antMatcher("/user/**")
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/webjars/**", "/resources/**","/css/**").permitAll()
                        .anyRequest().hasAuthority("ROLE_USER")

                )

                .formLogin((form) -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/user/home")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

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

    public void setUserServiceImp(UserServiceImp userServiceImp) {
        this.userDetailsService = userServiceImp;
    }
}
