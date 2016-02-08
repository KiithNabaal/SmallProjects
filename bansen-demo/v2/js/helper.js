function showComponents() {
	var items = document.getElementsByClassName("option");

	var i;

	var itemStyle = "background:linear-gradient(red, #ff8080);height:10%;width:100%;text-align:center;visibility:visible;";

	for(i = 0; i < items.length; i++) {
		items[i].setAttribute("style", itemStyle);
	}
}

function hideComponents() {
	var items = document.getElementsByClassName("option");

	var i;

	var itemStyle = "background:linear-gradient(red, #ff8080);height:10%;width:100%;text-align:center;visibility:hidden;";

	for(i = 0; i < items.length; i++) {
		items[i].setAttribute("style", itemStyle);
	}
}