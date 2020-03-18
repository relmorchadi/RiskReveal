package com.scor.rr.proxy.config.security;

import com.scor.rr.repository.UserRrRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;


@Configuration
@EnableWebSecurity
@Profile("dev")
public class WebSecurityConfigDev extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(WebSecurityConfigDev.class);

    @Autowired
    UserRrRepository userRepository;

    @Autowired
    AuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and().cors().and().authorizeRequests()
                .antMatchers("/api/**").authenticated().and().addFilterBefore(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
                WebSecurityConfigDev.this.logger.info("invoke filter auth");
                UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken("U011192", null, Collections.emptyList());
                userRepository.findByUserCode(userPassToken.getName()).ifPresent((e) -> {
                    getContext().setAuthentication(userPassToken);
                    WebSecurityConfigDev.this.logger.info("set username password on the SpringSecurityContext");
                });
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            }
        }, UsernamePasswordAuthenticationFilter.class);
    }
}
