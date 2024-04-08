package com.spring_sec.spring_ms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.spring_sec.spring_ms.util.security.jwt.AuthEntryPointJwt;
import com.spring_sec.spring_ms.util.security.jwt.AuthTokenFilter;
import com.spring_sec.spring_ms.util.security.services.UserDetailsServiceImpl;

import jakarta.servlet.ServletException;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.cors(cors -> cors.disable())
              .csrf(csrf -> csrf
                      .disable())
              .exceptionHandling(handling -> handling
                      .authenticationEntryPoint(unauthorizedHandler))
              .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authorizeHttpRequests(requests -> requests
                      .requestMatchers("/api/auth/**")
                      .permitAll()
                      .requestMatchers("/api/test/**")
                      .permitAll()
                      .anyRequest()
                      .authenticated())
              .logout(logout -> logout
                      .logoutUrl("/api/logout")
                      .clearAuthentication(true)
                      .addLogoutHandler((request, response, auth) -> {
                          try {
                              request.logout();
                          } catch (ServletException e) {
                              System.out.println(e.getMessage());
                          }
                      })
                      .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
