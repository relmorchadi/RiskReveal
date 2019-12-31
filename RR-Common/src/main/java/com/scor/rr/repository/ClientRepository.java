package com.scor.rr.repository;

import com.scor.rr.domain.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by u004602 on 27/12/2019.
 */
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {
    ClientEntity findByClientnumber(String clientnumber);
}
