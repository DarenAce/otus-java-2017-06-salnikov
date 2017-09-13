function refresh() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status == 200) {
            document.getElementById('hitCount').innerHTML = JSON.parse(this.responseText).hitCount;
            document.getElementById('missCount').innerHTML = JSON.parse(this.responseText).missCount;
            document.getElementById('hitRatio').innerHTML = JSON.parse(this.responseText).hitRatio;
        }
    };

    xhttp.open("GET", "/cache", true);
    xhttp.send();
}