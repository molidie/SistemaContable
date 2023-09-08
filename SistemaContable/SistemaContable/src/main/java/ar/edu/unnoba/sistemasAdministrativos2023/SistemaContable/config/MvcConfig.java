package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by jpgm on 31/10/22.
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/login").setViewName("/admin/login");
        registry.addViewController("/user/login").setViewName("/user/login");
    }
}