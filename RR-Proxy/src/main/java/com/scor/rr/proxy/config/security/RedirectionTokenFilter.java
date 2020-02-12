package com.scor.rr.proxy.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class RedirectionTokenFilter
        extends GenericFilterBean
{
    public RedirectionTokenFilter() {}

    Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    protected RedirectStrategy getRedirectStrategy() { return redirectStrategy; }




    protected WebApplicationContext getWebApplicationContext()
    {
        ServletContext servletContext = getServletContext();

        return servletContext != null ? WebApplicationContextUtils.getWebApplicationContext(servletContext) : null;
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        logger.debug("Redirection Filter doFilter");
        chain.doFilter(request, response);
    }



    protected String getRequestPath(HttpServletRequest httpServletRequest)
    {
        String requestPath = httpServletRequest.getRequestURI();
        return requestPath.replace(httpServletRequest.getContextPath() + "/", "");
    }
}

