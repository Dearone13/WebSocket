package com.WebSocket.cone.config;

import com.WebSocket.cone.chat.ChatMessage;
import com.WebSocket.cone.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener  //Metodo que permite saber si un usuario a dejado la sesión
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ){
        StompHeaderAccessor headerAccessor =StompHeaderAccessor.wrap(event.getMessage()); //Recibe mensaje del DisconnectEvent
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null){
            log.info("El usuario esta desconectado: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            //Informamos a todos los usuarios de la desconexión
            messageTemplate.convertAndSend("/topic/public",chatMessage);
        }
    }
}