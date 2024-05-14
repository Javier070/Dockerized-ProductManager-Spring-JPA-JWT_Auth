package com.jm.tfg.Token;

import com.jm.tfg.Token.JWT.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration //esta clase puede ser utilizada para definir beans o importar clases de configuración adicionales.
@EnableWebSecurity //esta anotación habilita la seguridad de Spring Security.
public class SecuriryConfig   {
    @Autowired
    private CustomerDetailsService customerDetailsService;
    @Autowired
    private JwtFilter jwtFilter;



    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/forgotPassword", "/user/registro", "/user/verifyToken", "/category/agregar").permitAll() // Permitir acceso sin autenticación a estas rutas,
//                        .requestMatchers("/**").permitAll() // Permitir acceso sin autenticación a todas las rutas
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // Requiere el rol "ADMIN" para las rutas bajo /admin
                        .anyRequest().authenticated())
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
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
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}
