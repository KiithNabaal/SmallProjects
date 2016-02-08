var express = require("express");
var app = express();
var fs = require("fs");

var d = require("data");

app.use(express.static("public"));
app.use(express.static("v2"));
app.use("/v2", express.static("public"));

var didAdvance = false;
var filtered = false;

app.get("/", function(req, res) {
	fs.readFile("v2/html/index.html", function(err, data) {
		res.set("Content-Type", "text/html");
		res.send(data);
		res.end();
	});
});

app.get("/channelData", function(req, res) {
	res.send(d.channelJSON);
});

app.get("/advanceTime", function(req, res) {
	didAdvance = !didAdvance;

	if(didAdvance && !filtered) {
		res.send(d.advanceChannelJSON);
	}
	else if(!didAdvance && !filtered) {
		res.send(d.channelJSON);
	}
	else if(didAdvance && filtered) {
		res.send(d.filterResults(d.advanceChannelJSON));
		d.refreshResults(d.advanceChannelJSON);
	}
	else if(!didAdvance && filtered) {
		res.send(d.filterResults(d.channelJSON));
		d.refreshResults(d.channelJSON);
	}
});

app.get("/filterFeatures", function(req, res) {
	filtered = !filtered;

	//Since we don't have a database - slight hack to properly manipulate data
	if(didAdvance && !filtered) {
		res.send(d.advanceChannelJSON);
	}
	else if(!didAdvance && !filtered) {
		res.send(d.channelJSON);
	}
	else if(didAdvance && filtered) {
		res.send(d.filterResults(d.advanceChannelJSON));
		d.refreshResults(d.advanceChannelJSON);
	}
	else if(!didAdvance && filtered) {
		res.send(d.filterResults(d.channelJSON));
		d.refreshResults(d.channelJSON);
	}
});

app.listen(8000);