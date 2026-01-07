package com.notas.demo.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Esta anotación marca la clase como configuración de Spring
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/*")  // Aplica a todos los endpoints bajo /api
                .allowedOrigins("http://localhost:3000")  // URL de tu frontend React
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("")  // Permite todos los headers
                .allowCredentials(true)  // Permite cookies/autenticación
                .maxAge(3600);  // Cache de preflight requests por 1 hora
    }
}