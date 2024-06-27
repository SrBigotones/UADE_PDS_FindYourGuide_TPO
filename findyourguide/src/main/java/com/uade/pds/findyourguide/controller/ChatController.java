package com.uade.pds.findyourguide.controller;

import com.uade.pds.findyourguide.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired private ChatService chatService;
    @PostMapping("/{channel}")
    public ResponseEntity enviarMensaje(@PathVariable String channel, @RequestBody String mensaje){
        try{
            chatService.enviarMensaje(channel, mensaje);
            return ResponseEntity.ok(null);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
