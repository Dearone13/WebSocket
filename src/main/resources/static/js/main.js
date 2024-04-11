'use strict';
//Valores de los input de la pagina
var usernamePage = document.querySelector('#username-page');
//Guarda el valor de la etiqueta html con id #username-page
var chatPage = document.querySelector('#chat-page');
//Guarda el valor de la etiqueta html con id #chat-page
var usernameForm = document.querySelector('#usernameForm');
//Guarda el valor de la etiqueta html con id #usernameForm
var messageForm = document.querySelector('#messageForm');
//Guarda el valor de la etiqueta html con id #messageForm
var messageInput = document.querySelector('#messageForm');
//Guarda el valor de la etiqueta html con id #messageForm
var messageArea = document.querySelector('#messageForm');
//Guarda el valor de la etiqueta html con id #messageForm
var connectingElement = document.querySelector('.connecting');
//Guarda el valor de la etiqueta html con class connecting

var stompClient = null;
//Crea variable que contendra las conexión WebSocket
var username = null;
//Variable que almacena la información con respecto a los usuarios.
var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];
//Colores definidos para los usuario definidos dentro un arreglo.

function connect(event) {
// Obtiene el nombre de usuario del elemento con el id 'name' del documento.
    username = document.querySelector('#name').value.trim();
  // Comprueba si se proporcionó un nombre de usuario válido.
    if(username) {
     // Oculta la página de selección de nombre de usuario, cambia lo elementos de la clase.
        usernamePage.classList.add('hidden');
         // Muestra la página de chat.
        chatPage.classList.remove('hidden');
        // Crea un nuevo objeto SockJS que representa la conexión WebSocket con el servidor en '/ws'.
        var socket = new SockJS('/ws');
        // Crea un cliente STOMP sobre el socket creado.
        stompClient = Stomp.over(socket);
        // Establece la conexión con el servidor, pasando funciones de devolución de llamada para cuando la conexión tenga éxito (onConnected) o falle (onError).
        stompClient.connect({}, onConnected, onError);
    }
     // Evita que el formulario se envíe y la página se recargue
    event.preventDefault();
}


function onConnected() {
    // Se suscribe a topic Public
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Le dice al servidor tu usuario
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
        /*Convierte a cadena de texto JSON*/
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
//Si cuenta un error la etiqueta html contendra el mensaje del error
    connectingElement.textContent = 'No se puedo conectar con el servidor del WebSocket. Por favor refresque y intente de nuevo!';
    connectingElement.style.color = 'red';
    //Le da una propiedad de color rojo al texto
}

//Funcion encargada de enviar mensajes
function sendMessage(event) {
    //Traer el valor de la etiqueta sin espacios en blanco
    var messageContent = messageInput.value.trim();
    //El información del mensaje coincide con la infromación STOMP del usuario
    if(messageContent && stompClient) {
    //Encapsula el mensaje en tipo Build en un objeto
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        //Encapsula el mensaje en formato JSON para ser enviado.
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        //El input queda vacio
        messageInput.value = '';
    }
    event.preventDefault();
}

//Función que recibe mensajes que recibe el punto de conexión payload
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    //Obtener el cuerpo de mensaje de Payload(punto de conexión)

    var messageElement = document.createElement('li');
    //Crear una etiqueta li.
     //Verifica si el usuario esta unido al chat
    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        //Agrega el elemento event-message si un usuario de unio
        message.content = message.sender + ' se unio!';
        //El cotnenido del JSON ahora gestiona 'se unio'
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' se fue!';
        //El cotnenido del JSON ahora gestiona 'se fue'
    } else {
    // Crea un elemento de avatar para mostrar la inicial del remitente.
    var avatarElement = document.createElement('i');
    // Crea un nodo de texto con la inicial del remitente.
    var avatarText = document.createTextNode(message.sender[0]);
    // Añade el nodo de texto al elemento de avatar.
    avatarElement.appendChild(avatarText);
    // Establece el color de fondo del avatar utilizando una función para obtener un color basado en el remitente.
    avatarElement.style['background-color'] = getAvatarColor(message.sender);

    // Agrega el elemento de avatar al elemento del mensaje.
    messageElement.appendChild(avatarElement);

    // Crea un elemento de nombre de usuario para mostrar el nombre del remitente.
    var usernameElement = document.createElement('span');
    // Crea un nodo de texto con el nombre del remitente.
    var usernameText = document.createTextNode(message.sender);
    // Añade el nodo de texto al elemento de nombre de usuario.
    usernameElement.appendChild(usernameText);
    // Agrega el elemento de nombre de usuario al elemento del mensaje.
    messageElement.appendChild(usernameElement);
    }

// Crea un elemento de párrafo para mostrar el contenido del mensaje.
var textElement = document.createElement('p');
// Crea un nodo de texto con el contenido del mensaje.
var messageText = document.createTextNode(message.content);
// Añade el nodo de texto al elemento de párrafo.
textElement.appendChild(messageText);

// Agrega el elemento de párrafo al elemento del mensaje.
messageElement.appendChild(textElement);

// Agrega el elemento del mensaje al área de mensajes en el DOM.
messageArea.appendChild(messageElement);

// Desplaza automáticamente el área de mensajes al final para mostrar el último mensaje.
messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
     //Cadena ordenada compacta
    var hash = 0;
    //For hasta el usuario de mensaje de largo
    for (var i = 0; i < messageSender.length; i++) {
        //trae la linea de cracteres char lo suma por la multiplicación de 31 * hash
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    //Trae un index del modulo de has y el tamaño del arreglo de colores de forma absoluta
    var index = Math.abs(hash % colors.length);
    //Trae el valor del arreglo.
    return colors[index];
}

// Agrega un evento de escucha al formulario de nombre de usuario para el evento 'submit'.
// Cuando se envía el formulario (se hace clic en el botón de enviar o se presiona Enter),
// se llama a la función 'connect' para manejar la conexión del usuario.
usernameForm.addEventListener('submit', connect, true);

// Agrega un evento de escucha al formulario de mensajes para el evento 'submit'.
// Cuando se envía el formulario (se hace clic en el botón de enviar o se presiona Enter),
// se llama a la función 'sendMessage' para manejar el envío del mensaje.
messageForm.addEventListener('submit', sendMessage, true);
