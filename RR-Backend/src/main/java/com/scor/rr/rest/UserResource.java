package com.scor.rr.rest;


import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.domain.dto.UserResponse;
import com.scor.rr.repository.UserRrRepository;
import com.scor.rr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserResource {

    @Autowired
    UserService userService;

    @Autowired
    UserRrRepository userRrRepository;

    @GetMapping("/{userName}")
    List<UserResponse> getUsers(@RequestParam String userName) {

        if(userName == null || userName.isEmpty()) {
            return userService.findAllUsers();
        } else {
            List<UserResponse> users= new ArrayList<UserResponse>();

            users.add(userService.findUser(userName));

            return users;
        }
    }


    @GetMapping("all")
    List<UserRrEntity> getAllUsers() {
        return userRrRepository.findAll();
    }


}
