console.log("Hello, Divination Matrix!");

// This is our page-state!
var readings;
var hexagrams;

var getHexagrams = function(callback) {
    $.get("/allHexagrams", function(data) {
        hexagrams = data;
        callback(null, hexagrams);
    });
}

var getReadings = function(callback) {
    $.get("/allReadings", function(data) {
        readings = data;
        callback(null, readings);
    });
}

var renderReading = function(next) {
    var reading = readings[Math.floor(Math.random() * (readings.length - 1))];
    var hexagram = hexagrams[reading.hexagramNumber - 1];
    console.log(hexagram);
    $('#name').text(hexagram.chinese + " " + hexagram.name);
    $("#space").text(hexagram.character);
    $("#pseudonym").text(reading.pseudonym);
    setTimeout(next, Math.floor(Math.random() * (300 - 100 + 1)) + 100);
};

async.parallel([
    getHexagrams,
    getReadings
], function(err, callback) {
    async.forever(renderReading);
});
