package com.WebSocket.cone.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/*Se identifica como un controlador WEB para recibir las peticiones HTTP para dirigir el flujo de un aplicación*/
@Controller
public class ChatController {

    //Metodo de enviar mensajes
    @MessageMapping("/chat.sendMessage") /*Tenemos un endpoint para recibir el mensaje,
     metodo de control de mensaje por medio de un patrón*/
    @SendTo("/topic/public")  //Que tema vamos a enviar en este caso a /topic
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage  //Contiene el body de nuestra request
    ){
        return chatMessage;
    }

    //Metodo de agregar usuarios
<<<<<<< HEAD
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
=======
    @MessageMapping("/chat.addUser") /*Tenemos un endpoint para recibir el mensaje,
    metodo de control de mensaje por medio de un patrón */
    @SendTo("/topic/public")  //Que tema vamos a enviar en este caso a /topic
>>>>>>> 2ff9053d462f76fb29158373ae6ff5269a96866e
    public ChatMessage addUser(
            /*Una clase base para trabajar con encabezados de mensaje en
            protocolos de mensajería simples que Admite patrones de mensajería básicos*/
            @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor
            /*Estraer carga del mensaje y transformar en encabezado.*/
    ){

        headerAccessor.getSessionAttributes().put("username",chatMessage.getSender());
        return  chatMessage;
    }

}
