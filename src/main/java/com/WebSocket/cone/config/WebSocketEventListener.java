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


@Component /*/Marca la clase como un componente ´para ser escaneado como bean(instanciar clase) para suministrar
en el contedor de Spring*/
@Slf4j /*Ofrece una API generica para logeo para simplificar los ingresos por medio de lombok*/
@RequiredArgsConstructor //Creaun constcutor con los atributos existentes
public class WebSocketEventListener {

    //Metodo que soporta la conexión por protoclo STOMP
    private final SimpMessageSendingOperations messagingTemplate;

    /*Genera una interface de listener */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        // SessionDisconnectEvent event ; Resgistra cuando una conexióndeun cliente WebSocket cierra
        // Extrae el nombre de usuario de los atributos de la sesión almacenados en los encabezados del mensaje.
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
            String username = (String) headerAccessor.getSessionAttributes().get("username");
        // Comprueba si se ha recuperado un nombre de usuario válido.
        if (username != null) {
            // Registra un mensaje informativo indicando que el usuario se ha desconectado.
            log.info("user disconnected: {}", username);
            // Crea un objeto ChatMessage utilizando el patrón de diseño Builder.
            var chatMessage = ChatMessage.builder()
                    // Establece el tipo de mensaje como "LEAVE" para indicar que el usuario se ha desconectado.
                    .type(MessageType.LEAVE)
                    // Establece el remitente del mensaje como el nombre de usuario que se ha desconectado.
                    .sender(username)
                    // Finaliza la construcción del objeto ChatMessage.
                    .build();
            //Envia mensaje a dado usuario
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

}