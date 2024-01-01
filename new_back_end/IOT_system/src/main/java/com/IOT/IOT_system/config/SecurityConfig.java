package com.IOT.IOT_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig  {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/RFID/listRFID").permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/user/save")).permitAll()
//                        .requestMatchers("/user/saveUserDTO","/post/**").authenticated()
//                        .anyRequest().authenticated()).build();
//    }




        @Bean
        public SecurityFilterChain securityConfiguration(HttpSecurity http) throws Exception{
            return http
                    // ...
                    .csrf((csrf)->csrf.disable())
                    .sessionManagement((session) -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests((auth)->auth
                            .requestMatchers("/user/**").permitAll()
                            .requestMatchers("/RFID/**").permitAll()
                            .requestMatchers("/map/**").permitAll()
                            .anyRequest().authenticated())
//                .addFilterAfter(new JwtTokenGenerator(), BasicAuthenticationFilter.class) //generates json token after validation of whatever request api is coming
//                .addFilterBefore(new JwtTokenValidation(),BasicAuthenticationFilter.class) // validates the request api before sending it into the application


                    .build();



        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }


//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8082"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }
}

