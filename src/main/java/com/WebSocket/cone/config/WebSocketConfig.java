package com.WebSocket.cone.config;

import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*Broker: Actua como un intermediario cuando viene un proceso de comunicación entre dos aplicaciones*/
/*Cuando se utiliza la compatibilidad con STOMP de Spring, la aplicación Spring WebSocket actúa como agente de STOMP para los clientes.
 Los mensajes se enrutan a @Controller métodos de control de mensajes o a un agente en memoria simple que realiza un seguimiento de las suscripciones y difunde mensajes a los usuarios suscritos.
 También puede configurar Spring para que funcione con un broker STOMP dedicado (como RabbitMQ, ActiveMQ y otros) para la difusión real de mensajes.
 En ese caso, Spring mantiene las conexiones TCP con el agente,
 le retransmite mensajes y pasa mensajes desde él a los clientes WebSocket conectados.*/
@Configuration // Es un analogo al contenedor XML en el cual se van instanciar las dependencias(suministro de objetos)
@EnableWebSocketMessageBroker //Habilita una configuración personalizada de un agente de mensajería del WebSocket
/*WebSocketMessageBrokerConfigurer : Define  un método de configuración de mensajes en este caso de protocolo STOMP a los clientes WebSocket */
    /*Agregue esta anotación a una clase para habilitar la cuenta
    respaldada por una agente mensajería a través de WebSocket mediante un subprotocolo
    de mensajería de nivel superior.*/
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {
    //Personaliza la configuración importada implementando la interface
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registra el punto final (endpoint) de los WebSockets
        registry.addEndpoint("/ws").withSockJS();
        //ws especifica la conexión de tipo webSocket
        // El endpoint "/ws" estará disponible para los clientes que deseen conectarse a través de WebSocket,
        // y conSockJS() permite que los navegadores que no soportan nativamente WebSocket aún puedan comunicarse
        // a través de WebSockets mediante la capa de abstracción SockJS.

    }

    @Override //sobreescritura de metodos
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // Configura el broker de mensajes
        registry.setApplicationDestinationPrefixes("/app");
        // Establece el prefijo para los mensajes enviados desde los clientes hacia los controladores
        // en el servidor. En este caso, todos los mensajes destinados a controladores en el servidor
        // deberán comenzar con "/app". Esto ayuda a distinguir entre los mensajes dirigidos al servidor
        // y aquellos dirigidos a los clientes.

        registry.enableSimpleBroker("/topic");
        // Habilita un broker simple para enviar mensajes desde el servidor hacia los clientes.
        // "/topic" es el prefijo para los canales de destino a los cuales los clientes pueden suscribirse
        // para recibir mensajes enviados desde el servidor.
    }

    }

