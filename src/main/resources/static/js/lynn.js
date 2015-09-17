console.log("Hello, Lynn!");

// This is our page-state!
var positionsMap;
var nodes;
var links;

// Let's see those cells!
var socket = new SockJS('/welcome');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/cellCulture', renderCellCulture);
    stompClient.subscribe('/topic/newCell', renderNewCell);
    stompClient.send("/app/welcome", {}, JSON.stringify({ 'id': 0 }));
});

var renderCellCulture = function(response) {
    console.log("We have cells!");

    var body = JSON.parse(response.body);
    var network = body["d3network"];
    positionsMap = network.positionMap;
    nodes = network.d3data.nodes;
    links = network.d3data.rels;

    console.log("Positions: " + JSON.stringify(positionsMap));
    console.log("Nodes: " + JSON.stringify(nodes));
    console.log("Links: " + JSON.stringify(links));

    dosomed3shit({
        nodes: nodes,
        links: links
    });
}

var dosomed3shit = function(graph) {
    var width = 1920,
        height = 500;

    var color = d3.scale.category20();

    var force = d3.layout.force()
        .charge(-120)
        .linkDistance(30)
        .size([width, height]);

    var svg = d3.select("body").append("svg")
        .attr("width", width)
        .attr("height", height);

    force
        .nodes(graph.nodes)
        .links(graph.links)
        .start();

    var link = svg.selectAll(".link")
        .data(graph.links)
        .enter().append("line")
        .attr("class", "link")
        .style("stroke-width", function(d) { return Math.sqrt(d.value); });

    var node = svg.selectAll(".node")
        .data(graph.nodes)
        .enter().append("circle")
        .attr("class", "node")
        .attr("r", 5)
        .style("fill", function(d) { return color(d.group); })
        .call(force.drag);

    node.append("title")
        .text(function(d) {
            return "I'm " + d.name + "! About me: " + d.about;
        });

    force.on("tick", function() {
        link.attr("x1", function(d) { return d.source.x; })
            .attr("y1", function(d) { return d.source.y; })
            .attr("x2", function(d) { return d.target.x; })
            .attr("y2", function(d) { return d.target.y; });

        node.attr("cx", function(d) { return d.x; })
            .attr("cy", function(d) { return d.y; });
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
