package com.scor.rr.repository.references;

import com.scor.rr.domain.entities.references.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	User findByCode(String code);

}