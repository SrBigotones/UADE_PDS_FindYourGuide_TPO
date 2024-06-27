package com.uade.pds.findyourguide.service.chat;

import com.uade.pds.findyourguide.model.Chat;
import com.uade.pds.findyourguide.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ChatService {

    private ChatConnection chatConnection;
    @Autowired
    private ChatRepository chatRepository;

    public ChatService(){
        this.chatConnection = new ChatSendBird();
    }



    public void enviarMensaje(String channel, String mensaje) throws NoSuchElementException {
        chatRepository.findChatByCanalSendBird(channel).orElseThrow();

        chatConnection.enviarMensaje(channel, mensaje);
    }
}
