package com.scor.rr.repository.cat.message;

import com.scor.rr.domain.entities.references.cat.message.MessageProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MessageProcess Repository
 *
 * @author HADDINI Zakariyae
 */
public interface MessageProcessRepository extends JpaRepository<MessageProcess, String> {

    MessageProcess findByProcessName(String process);
}