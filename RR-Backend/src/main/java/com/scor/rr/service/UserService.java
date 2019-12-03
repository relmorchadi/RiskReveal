package com.scor.rr.service;

import com.scor.rr.domain.TargetBuild.UserTag;
import com.scor.rr.domain.dto.UserResponse;
import com.scor.rr.repository.UserRrRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class UserService {

    @Autowired
    UserRrRepository userRrRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<UserResponse> findAllUsers() {
        return userRrRepository.findAll().stream().map(e -> modelMapper.map(e, UserResponse.class)).collect(Collectors.toList());
    }

    public UserResponse findUser(String userName) {
        //return modelMapper.map(userRrRepository.findByUserName(userName).get(0), UserResponse.class);
        return null;
    }

    public Set<UserTag> findTagOfUser(Integer userId) {
        return null;
    }

}
