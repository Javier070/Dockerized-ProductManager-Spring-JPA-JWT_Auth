package com.jm.tfg.Token;

import com.jm.tfg.Token.JWT.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration //Indica que esta clase es una clase de configuración de Spring.
// Los métodos anotados con @Bean dentro de esta clase se usarán para crear y configurar beans que serán administrados por el contenedor de Spring.

@EnableWebSecurity //esta anotación habilita la seguridad de Spring Security.

public class SecuriryConfig   {
    @Autowired
    private CustomerDetailsService customerDetailsService;
    @Autowired
    private JwtFilter jwtFilter;
    //configuera se encarga de de



    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(configurer -> configurer.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/forgotPassword", "/user/registro", "/user/verifyToken", "/category", "/product").permitAll() // Permitir acceso sin autenticación a estas rutas,
                        .requestMatchers("/**").permitAll() // Permitir acceso sin autenticación a todas las rutas
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // Requiere el rol "ADMIN" para las rutas bajo /admin
                      ) //todas las rutas deben ser autenticadas
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://127.0.0.1:5500");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
//prueba a borra el método de abajo
/*    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable().
                 authorizeHttpRequests(

                 )

    }*/




}
