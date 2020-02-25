package com.scor.rr.proxy.service.implementation;

import com.scor.rr.proxy.service.abstraction.HelperServiceAbs;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HelperService implements HelperServiceAbs {

    @Override
    public String getUserId() {
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        return userName.contains("@") ? userName.split("@")[0] : userName;
    }
}
