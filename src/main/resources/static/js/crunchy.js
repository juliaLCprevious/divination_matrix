console.log("Hello, Divination Matrix!");

var socket = new SockJS('/addReading');
var stompClient = Stomp.over(socket);
var readings;
var hexagrams;

var getHexagrams = function(callback) {
    $.get("/allHexagrams", function(data) {
        hexagrams = data;
        callback(null);
    });
}

var getReadings = function(callback) {
    $.get("/allReadings", function(data) {
        readings = data;
        callback(null);
    });
}

var prepareSockets = function(callback) {
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/newReading', loadNewReading);
        callback(null);
    });
}

var loadNewReading = function(socketResponse) {
    var newReading = JSON.parse(socketResponse.body);
    console.log("New reading! " + newReading);
    readings.push(newReading);
}

var renderReading = function(next) {
    var reading = readings[Math.floor(Math.random() * (readings.length))];
    var hexagram = hexagrams[reading.hexagramNumber - 1];
    console.log(reading);
    $('#name').text(hexagram.chinese + " " + hexagram.name);
    $("#space").text(hexagram.character);
    $("#pseudonym").text(reading.pseudonym);
    setTimeout(next, Math.floor(Math.random() * (200 - 50 + 1)) + 50);
};

async.parallel([
    getHexagrams,
    getReadings,
    prepareSockets
], function(err, callback) {
    async.forever(renderReading);
});
