package com.scor.rr.proxy.config.security;


import com.scor.rr.repository.UserRrRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.sun.GlobalSunJaasKerberosConfig;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.kerberos.client.config.SunJaasKrb5LoginConfig;
import org.springframework.security.kerberos.client.ldap.KerberosLdapContextSource;
import org.springframework.security.kerberos.web.authentication.SpnegoAuthenticationProcessingFilter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.DelegatingAccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@EnableWebSecurity()
@Configuration
@Profile("!dev")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    AuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserRrRepository userRepository;

    @Value("${ldap.domain}")
    private String ldapDomain;
    @Value("${ldap.url}")
    private String ldapUrl;
    @Value("${keytab.location}")
    private Resource keytabLocation;
    @Value("${krbConfLocation.path}")
    private String krbConfLocation;
    @Value("${krbConfLocation.debug}")
    private boolean krbConfDebug;
    @Value("${service.principal}")
    private String servicePrincipal;
    @Value("${service.principal.debug}")
    private boolean servicePrincipalDebug = true;


    public WebSecurityConfig() {

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers("/api/**").authenticated().and().httpBasic()
                .authenticationEntryPoint(restSpenegoEntryPoint()).and()
                .addFilterBefore(spnegoAuthenticationProcessingFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(redirectionTokenFilter(), ExceptionTranslationFilter.class)
                .addFilterAfter(new OncePerRequestFilter() {
                    @Override
                    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
                        Authentication authentication = Optional.ofNullable(getContext().getAuthentication()).orElseGet(() -> new UsernamePasswordAuthenticationToken("", "", null));
                        Boolean isAppUser = userRepository.findByUserCode(authentication.getName().split("@")[0]).isPresent();
                        getContext().setAuthentication(isAppUser ? authentication : null);
                        filterChain.doFilter(httpServletRequest, httpServletResponse);
                    }
                }, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response
        // must not be the wildcard '*' when the request's credentials mode is
        // 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        // configuration.setAllowedHeaders(Arrays.asList("Content-Type",
        // "X-Requested-With", "accept", "Origin",
        // "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        configuration
                .setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-Requested-With"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(activeDirectoryLdapAuthenticationProvider())
                .authenticationProvider(kerberosServiceAuthenticationProvider());
    }

    @Bean
    public SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter() throws Exception {
        SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter = new SpnegoAuthenticationProcessingFilter();
        // spnegoAuthenticationProcessingFilter.setSuccessHandler(customAuthenticationSuccessHandler());
        spnegoAuthenticationProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        return spnegoAuthenticationProcessingFilter;
    }


    @Bean
    public RestSpnegoEntryPoint restSpenegoEntryPoint() {
        return new RestSpnegoEntryPoint();
    }

    @Bean
    public AccessDeniedHandlerImpl customAccessDeniedHandlerImpl() {
        return new AccessDeniedHandlerImpl() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
                response.setStatus(401);
                response.getWriter().flush();
            }
        };
    }

    @Bean
    public DelegatingAccessDeniedHandler delegatingAccessDeniedHandler() {
        LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> handlers = new LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler>();
        handlers.put(AccessDeniedException.class,
                customAccessDeniedHandlerImpl());
        return new DelegatingAccessDeniedHandler(handlers, customAccessDeniedHandlerImpl());

    }

    @Bean
    ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        return new ActiveDirectoryLdapAuthenticationProvider(ldapDomain, ldapUrl);
    }

    @Bean
    KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider() {
        KerberosServiceAuthenticationProvider ksap = new KerberosServiceAuthenticationProvider();
        ksap.setTicketValidator(sunJaasKerberosTicketValidator());
        ksap.setUserDetailsService(ldapUserDetailsService());
        return ksap;
    }


    @Bean
    GlobalSunJaasKerberosConfig globalSunJaasKerberosConfig() {
        GlobalSunJaasKerberosConfig globalSunJaasKerberosConfig = new GlobalSunJaasKerberosConfig();
        globalSunJaasKerberosConfig.setDebug(krbConfDebug);
        globalSunJaasKerberosConfig.setKrbConfLocation(krbConfLocation);
        return globalSunJaasKerberosConfig;
    }

    @Bean
    SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator() {
        if (krbConfDebug) {
            logger.info("Set value for sun.security.krb5.debug = true");
            System.setProperty("sun.security.krb5.debug", Boolean.toString(this.krbConfDebug));
        }

        BufferedReader br = null;
        BufferedReader br_ = null;
        FileReader fr = null;
        FileReader fr_ = null;

        try { // br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(keytabLocation.getFile());
            fr_ = new FileReader(krbConfLocation);
            br = new BufferedReader(fr);
            br_ = new BufferedReader(fr_);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                logger.info(sCurrentLine);
            }
            while ((sCurrentLine = br_.readLine()) != null) {
                logger.info(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
                if (br_ != null)
                    br_.close();
                if (fr_ != null)
                    fr_.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();
        ticketValidator.setKeyTabLocation(keytabLocation);
        ticketValidator.setServicePrincipal(servicePrincipal);
        ticketValidator.setHoldOnToGSSContext(false);
        ticketValidator.setDebug(servicePrincipalDebug);
        return ticketValidator;
    }

    @Bean
    LdapUserDetailsService ldapUserDetailsService() {
        return new LdapUserDetailsService(filterBasedLdapUserSearch(), ldapAuthoritiesPopulator());
    }

    @Bean
    CustomFilterBasedLdapUserSearch filterBasedLdapUserSearch() {
        return new CustomFilterBasedLdapUserSearch("DC=eu,DC=scor,DC=local", "sAMAccountName={0}",
                kerberosLdapContextSource());
    }

    @Bean
    SunJaasKrb5LoginConfig loginConfig() {
        SunJaasKrb5LoginConfig loginConfig = new SunJaasKrb5LoginConfig();
        loginConfig.setKeyTabLocation(keytabLocation);
        loginConfig.setServicePrincipal(servicePrincipal);
        loginConfig.setDebug(servicePrincipalDebug);
        loginConfig.setIsInitiator(true);
        return loginConfig;

    }

    @Bean
    RedirectionTokenFilter redirectionTokenFilter() {
        return new RedirectionTokenFilter();
    }


    @Bean
    KerberosLdapContextSource kerberosLdapContextSource() {
        KerberosLdapContextSource kerberosLdapContextSource = new KerberosLdapContextSource(ldapUrl);
        kerberosLdapContextSource.setLoginConfig(loginConfig());
        return kerberosLdapContextSource;
    }

    @Bean
    CustomLdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
        CustomLdapAuthoritiesPopulator ldapAuthoritiesPopulator = new CustomLdapAuthoritiesPopulator(
                kerberosLdapContextSource(), "CN=Users,DC=eu,DC=scor,DC=local");
        ldapAuthoritiesPopulator.setGroupRoleAttribute("ou");
        ldapAuthoritiesPopulator.setSearchSubtree(false);
        ldapAuthoritiesPopulator.setRolePrefix("ROLE_");
        ldapAuthoritiesPopulator.setConvertToUpperCase(true);
        return ldapAuthoritiesPopulator;
    }

}
