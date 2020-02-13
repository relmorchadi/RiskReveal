package com.scor.rr.proxy.service.implementation;

import com.scor.rr.proxy.service.abstraction.HelperServiceAbs;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HelperService implements HelperServiceAbs {

    @Override
    public String getUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName().split("@")[0];
    }
}
