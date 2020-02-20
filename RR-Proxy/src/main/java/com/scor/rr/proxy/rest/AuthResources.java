package com.scor.rr.proxy.rest;


import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.proxy.config.security.JwtTokenProvider;
import com.scor.rr.proxy.dto.UserDto;
import com.scor.rr.proxy.service.abstraction.HelperServiceAbs;
import com.scor.rr.proxy.service.abstraction.UserServiceAbs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthResources {


    @Autowired
    UserServiceAbs userService;

    @Autowired
    HelperServiceAbs helperService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @GetMapping("login")
    public ResponseEntity<?> login() {
        log.info("Invoking login method");
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            try {
                String name = SecurityContextHolder.getContext().getAuthentication().getName();
                log.info("#11 value of username is ===> " + name);
                String userId = helperService.getUserId();
                UserDto userDto = new UserDto();
                log.info("#11 value of userId is ===> " + userId);

                Optional<UserRrEntity> userOpt=userService.getUserByUserId(userId);
                if(userOpt.isPresent()){
                    UserRrEntity user = userOpt.get();
                    userDto.setFullName(user.getFirstName() + " " + user.getLastName());
                    userDto.setCode(user.getUserCode());
                    userDto.setRole(user.getRole());
                    userDto.setJwtToken(jwtTokenProvider.generateToken(SecurityContextHolder.getContext().getAuthentication().getName().split("@")[0]));
                    return new ResponseEntity<>(userDto, HttpStatus.OK);
                }else {
                    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
