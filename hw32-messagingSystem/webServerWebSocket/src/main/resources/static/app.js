let stompClient = null;

const clientSv = () => stompClient.send("/app/clientSave", {}, JSON.stringify({'name': $("#name").val(), 'address':$("#address").val(), 'phone':$("#phone").val()}))

const addClient = (client) => {
    var phoneList = "";
    client.phones.forEach(function(elem, index) {
                          	phoneList = phoneList + elem.number + " <br> ";
    });
    $("#clientsList").append(
        "<tr>"+
            "<td>" + client.id + "</td>" +
            "<td>" + client.name + "</td>" +
            "<td>" + client.adress.street + "</td>" +
            "<td>" + phoneList + "</td>" +
        "</tr>");
}

const loadClient = (loadClient) => {
    var clientStr = "";
    for (i in loadClient) {
        addClient(loadClient[i])
    };
}

const serverConnect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        //stompClient.subscribe('/topic/response', (message) => addClient(JSON.parse(message.body)));
        stompClient.subscribe('/topic/response', (message) => loadClient(JSON.parse(message.body)));
        stompClient.subscribe('/topic/load', (message) => loadClient(JSON.parse(message.body)));
        stompClient.send("/app/clientLoad", {}, {});
    });
}

$(document).ready(serverConnect);

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#clientSave").click(clientSv);
});