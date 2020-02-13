package com.scor.rr.proxy.service.abstraction;

import com.scor.rr.domain.UserRrEntity;

import java.util.Optional;

public interface UserServiceAbs {
    Optional<UserRrEntity> getUserByUserId(String code);
}
