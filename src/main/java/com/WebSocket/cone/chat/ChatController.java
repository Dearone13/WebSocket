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
    @MessageMapping("/chat.addUser") //Establece conexión entre el usuario y el WebSocket
    @SendTo("/topic/public")  //Que tema vamos a enviar en este caso a /topic
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor
    ){
        //Agrega a usuario en una sesión del webSocket
        headerAccessor.getSessionAttributes().put("username",chatMessage.getSender());
        return  chatMessage;
    }

}
