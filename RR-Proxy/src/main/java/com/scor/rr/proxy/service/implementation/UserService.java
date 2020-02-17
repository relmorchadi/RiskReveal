package com.scor.rr.proxy.service.implementation;

import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.proxy.service.abstraction.UserServiceAbs;
import com.scor.rr.repository.UserRrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceAbs {

    @Autowired
    UserRrRepository userRepository;

    @Override
    public Optional<UserRrEntity> getUserByUserId(String code){
        return userRepository.findByUserCode(code);
    }
}
