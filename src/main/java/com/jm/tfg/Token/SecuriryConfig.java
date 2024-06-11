package com.jm.tfg.Token;

import com.jm.tfg.Token.JWT.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
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


    /**
     * Configura las reglas de seguridad HTTP.
     * Deshabilita CSRF, permite CORS, y establece políticas de autorización.
     *
     * @param http objeto HttpSecurity para configurar la seguridad web.
     * @return SecurityFilterChain configurado.
     * @throws Exception si ocurre algún error durante la configuración.
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(configurer -> configurer.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/user/login", "/user/forgotPassword", "/user/registro", "/user/verifyToken").permitAll()
                        .requestMatchers("/Inicio/login/login.html", "/Inicio/login/registro/formulario.html").permitAll()
                        .anyRequest().authenticated()

//
//
//                        .requestMatchers("/user/login", "/user/forgotPassword", "/user/registro",
//                                "/user/verifyToken", "/category", "/product").permitAll() // Permitir acceso sin autenticación a estas rutas,
//                        .requestMatchers("/**").authenticated() // Permitir acceso sin autenticación a todas las rutas
////                        .requestMatchers("/admin/**").hasRole("ADMIN") // Requiere el rol "ADMIN" para las rutas bajo /admin
                      ).userDetailsService(customerDetailsService) //todas las rutas deben ser autenticadas
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    /**
     * Proporciona un bean de AuthenticationManager.
     * El AuthenticationManager es usado para gestionar la autenticación de usuarios.
     *
     * Este bean es inyectado en la implementación de servicio de usuario (`UserServiceImpl`)
     * para autenticar las credenciales del usuario.
     *
     * @param authenticationConfiguration configuración de autenticación.
     * @return AuthenticationManager.
     * @throws Exception si ocurre algún error durante la configuración.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura la política CORS.
     * Permite solicitudes desde el origen especificado con cualquier encabezado y método.
     *
     * @return CorsConfigurationSource configurado.
     */

    // configuration: configuración que  incluye cosas como qué orígenes (dominios) están permitidos, qué métodos HTTP están permitidos, qué encabezados están permitidos, si se permite el uso de credenciales, etc

    //source: se encarga de aplicar configuraciones de CORS a diferentes rutas (URLs) del servidor.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration(); // Crear una nueva instancia de CorsConfiguration
        configuration.addAllowedOrigin("http://127.0.0.1:5500"); // Permitir solicitudes desde este origen específico

        configuration.addAllowedHeader("*"); // Permitir todos los encabezados
//       configuration.addAllowedMethod("DELETE"); // Permitir específicamente el método DELETE
        configuration.addAllowedMethod("*"); // Permitir todos los métodos HTTP
        configuration.setAllowCredentials(true); // Permitir el envío de credenciales en las solicitudes CORS

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Crear una instancia de UrlBasedCorsConfigurationSource

            source.registerCorsConfiguration("/**", configuration); // Registrar la configuración de CORS para todas las rutas
        return source; // Devolver el objeto CorsConfigurationSource configurado
    }
 //addAllowedHeader: la aplicación utiliza encabezados personalizados para tokens de autenticación (como JWT),
    //sin esta configuración, tales solicitudes son bloqueadas.

//.setAllowCredentials(true): habilitar autenticación basada en tokens (como JWT en el encabezado de autorización),
// esta configuración es crucial para que estas credenciales se envíen y acepten correctamente entre el front-end y el back-end.


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
