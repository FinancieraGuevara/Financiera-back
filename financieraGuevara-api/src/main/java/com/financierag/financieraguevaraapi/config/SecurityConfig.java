package com.financierag.financieraguevaraapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth-> auth.requestMatchers("/public/**").permitAll()
                        .requestMatchers("/private").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                ) .formLogin(login -> login // Página de login personalizada
                        .loginProcessingUrl("/login")  // Ruta para procesar el login (POST)
                        .defaultSuccessUrl("/public/users", true)  // Redirige después de un login exitoso
                        .failureUrl("/login?error")  // Redirige después de un login fallido
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Ruta personalizada para logout
                        .logoutSuccessUrl("/login")  // Redirige después de logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")  // Elimina la cookie de sesión
                )

                .build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        // Configura tu autenticación aquí
        authenticationManagerBuilder.userDetailsService(userDetailsService); // Asegúrate de configurar el UserDetailsService
        return authenticationManagerBuilder.build();
    }

}