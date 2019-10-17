package com.scor.rr.repository.cat.message;


import com.scor.rr.domain.entities.references.cat.message.MessageNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * MessageNotification Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface MessageNotificationRepository extends JpaRepository<MessageNotification, String> {

	@Transactional
	Integer deleteByCatRequestId(String catRequestId);

}