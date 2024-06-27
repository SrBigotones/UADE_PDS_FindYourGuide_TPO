package com.uade.pds.findyourguide.service.chat;

public class ChatSendBird implements ChatConnection{
    @Override
    public void enviarMensaje(String channel, String mensaje) {
        System.out.println("Se envia mensaje por medio de sendbird");
    }
}
