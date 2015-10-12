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
    var reading = readings[Math.floor(Math.random() * (readings.length))];
    var hexagram = hexagrams[reading.hexagramNumber];
    console.log(hexagram);
    setTimeout(next, 200);
};

async.parallel([
    getHexagrams,
    getReadings
], function(err, callback) {
    async.forever(renderReading);
});
