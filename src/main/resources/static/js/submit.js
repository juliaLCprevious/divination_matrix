console.log("Hello, Divination Matrix!");

var socket = new SockJS('/addReading');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
});

$("#submit-reading").on('click', function(event) {
    var newReading = {
        pseudonym: $('#pseudonym').val(),
        hexagramNumber: $('#hexagram').val()
    }
    $('#pseudonym').val("");
    $('#hexagram').val("");
    if (newReading.hexagramNumber < 1 || newReading.hexagramNumber > 64) {
        console.log("Not a valid hexagram, you jerk!!");
        return;
    }
    stompClient.send("/app/addReading", {}, JSON.stringify(newReading));
});
