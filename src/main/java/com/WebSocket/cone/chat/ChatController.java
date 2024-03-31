package com.WebSocket.cone.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    //Metodo de enviar mensajes
    @MessageMapping("/chat.sendMessage") //Tenemos un endpoint para recibir el mensaje
    @SendTo("/topic/public")  //Que tema vamos a enviar en este caso a /topic
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage  //Contiene el body de nuestra request
    ){
        return chatMessage;
    }

    //Metodo de agregar usuarios
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor
    ){

        headerAccessor.getSessionAttributes().put("username",chatMessage.getSender());
        return  chatMessage;
    }

}
