console.log("Hello, Lynn!");

// Let's see those cells!
var socket = new SockJS('/welcome');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/cellCulture', renderCellCulture);
    stompClient.send("/app/welcome", {}, JSON.stringify({ 'id': 0 }));
});

var renderCellCulture = function(response) {
    console.log("We have cells!");

    var cellCulture = JSON.parse(response.body);
    cellCulture.cells.forEach(function(cell) {
        $('#cells').append("<p>(~" + cell.name + "~)</p>")
    });

}
