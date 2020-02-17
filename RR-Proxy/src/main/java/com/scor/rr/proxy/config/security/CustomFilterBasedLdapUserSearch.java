package com.scor.rr.proxy.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.util.StringUtils;


public class CustomFilterBasedLdapUserSearch extends FilterBasedLdapUserSearch {
    private static final Logger logger = LoggerFactory.getLogger(CustomFilterBasedLdapUserSearch.class);

    public CustomFilterBasedLdapUserSearch(String searchBase, String searchFilter, BaseLdapPathContextSource contextSource) {
        super(searchBase, searchFilter, contextSource);
    }

    public static final String extractUser(String username) {
        if (StringUtils.isEmpty(username)) {
            return username;
        }
        String userId = username;
        while (userId.contains("@")) {
            userId = userId.substring(0, userId.indexOf("@"));
        }

        return userId.toUpperCase();
    }

    @Override
    public DirContextOperations searchForUser(String username) {
        String userId = extractUser(username);

        logger.debug("LDAP search done for user {} with user id {}.", username, userId);

        return super.searchForUser(userId);
    }
}