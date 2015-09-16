console.log("Hello, Lynn!");

// Let's see those cells!
var socket = new SockJS('/welcome');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/cellCulture', renderCellCulture);
    stompClient.subscribe('/topic/newCell', renderCellCulture);
    stompClient.send("/app/welcome", {}, JSON.stringify({ 'id': 0 }));
});

var renderCellCulture = function(response) {
    console.log("We have cells!");

    var cellCulture = JSON.parse(response.body);
    cellCulture.cells.forEach(function(cell) {
        $('#cells').append("<p>(~" + cell.name + "~)</p>")
    });

}

$("#create-cell").on('click', function(event) {
    var newCell = {
        name: $('#name').val(),
        about: $('#about').val()
    }
    stompClient.send("/app/createCell", {}, JSON.stringify(newCell));
});

var renderNewCell = function(response) {
    console.log("We have a new cell!");

    var newCell = JSON.parse(response.body);
    $('#cells').append("<p>(~" + newCell.newCell.name + "~)</p>")
}
