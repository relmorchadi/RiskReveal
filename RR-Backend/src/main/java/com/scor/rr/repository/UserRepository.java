package com.scor.rr.repository;

import com.scor.rr.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer>{
    List<User> findByUserName(String userName);
}
