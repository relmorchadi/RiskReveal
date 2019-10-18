package com.scor.rr.repository.omega;

import com.scor.rr.domain.entities.references.omega.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
