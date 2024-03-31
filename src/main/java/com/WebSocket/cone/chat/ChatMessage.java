package com.WebSocket.cone.chat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*Anotaciones de tipo lombok qque generan Getter, setters, Costructor vacio, constructor
con todos los elementos y un constrcutor de tipo Builder
 */

public class ChatMessage {

    private String content;
    private String sender;
    private MessageType type;

}
