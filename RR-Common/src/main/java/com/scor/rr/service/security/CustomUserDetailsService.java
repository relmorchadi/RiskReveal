package com.scor.rr.service.security;


import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.repository.UserRrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRrRepository userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String code) {

        UserPrincipal userPrincipal = new UserPrincipal();

        userRepo.findByUserCode(code).ifPresent((UserRrEntity user) -> {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            userPrincipal.setUser(user);
            userPrincipal.setAuthorities(authorities);
        });


        return userPrincipal;
    }

    public UserDetails loadUserById(String id) {
        UserRrEntity user = userRepo.findByUserCode(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }

}
