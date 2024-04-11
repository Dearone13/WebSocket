package com.WebSocket.cone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//Anotación que habilita el servidor Springboot en el puerto 8080
@SpringBootApplication
//Public es un modificadorde acceso para cualquier tipo de clase
public class ChatApplication {
    //Define al metodo como publico que no necesita de una instancia para acceder al ser de tipo abstracto
    //Void define la capacidad de que la función no retornada ningun elemento
    public static void main(String[] args ) {
        //Define un parametro de entrada tipo arreglo para entrada de flujo de cadena
        SpringApplication.run(ChatApplication.class, args);
        //Proporciona una clase que incializa el servidor donde se llama a si mismo para instanciarse
        //por medio del .class y el paso de args en cadena de texto.
    }
}
