package com.uade.pds.findyourguide.repository;

import com.uade.pds.findyourguide.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {


    Optional<Chat> findChatByCanalSendBird(String canalSendBid);
}
