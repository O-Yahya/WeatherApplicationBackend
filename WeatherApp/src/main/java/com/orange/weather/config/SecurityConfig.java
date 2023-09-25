package com.orange.weather.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth
                            // anyone can register or login
                            .requestMatchers("/api/auth/**")
                            .permitAll()

                            // any authenticated user, admin or superuser can view all weather details and notes for the current day
                            .requestMatchers("/api/weather", "/api/weather/{city}", "/api/notes/todaysnotes").hasAnyAuthority("USER", "ADMIN", "SUPERUSER")

                            // only authenticated users and admins can view and edit their profile data
                            .requestMatchers("/api/myprofile").hasAnyAuthority("USER", "ADMIN")

                            // only authenticated superuser can create and delete admins
                            .requestMatchers("/api/admins").hasAuthority("SUPERUSER")
                            .requestMatchers(HttpMethod.DELETE, "/api/admins/{email}").hasAuthority("SUPERUSER")

                            // only authenticated admins or superuser can add new notes
                            .requestMatchers(HttpMethod.POST,"/api/notes").hasAnyAuthority("ADMIN", "SUPERUSER")

                            // only authenticated superuser can view all notes
                            .requestMatchers(HttpMethod.GET, "/api/notes").hasAuthority("SUPERUSER")

                            // only authenticated admin can view all their posted notes
                            .requestMatchers(HttpMethod.GET, "/api/mynotes").hasAuthority("ADMIN")

                            // no one can delete notes
                            .requestMatchers(HttpMethod.DELETE, "/api/notes").denyAll()
                            .anyRequest().authenticated();

                })

                .sessionManagement(ses ->
                        ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
