package com.scor.rr.proxy.config.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;

import java.util.HashSet;
import java.util.Set;


public class CustomLdapAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator {
    public static final String ROLE_USER = "ROLE_USER";
    private static final Logger logger = LoggerFactory.getLogger(CustomLdapAuthoritiesPopulator.class);
    public CustomLdapAuthoritiesPopulator(ContextSource contextSource, String groupSearchBase) {
        super(contextSource, groupSearchBase);
    }

    @Override
    protected Set<GrantedAuthority> getAdditionalRoles(DirContextOperations user, String username) {
        logger.info("Add specific role {} to {}", ROLE_USER, username);

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(ROLE_USER));
        return authorities;
    }
}

