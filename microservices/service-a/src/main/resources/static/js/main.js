function loadMessages () {

    this.source = null;

    this.start = function () {

        var messageTable = document.getElementById("messages");

        this.source = new EventSource("/message/stream/interval");

        this.source.addEventListener("message", function (event) {

            // These events are JSON, so parsing and DOM fiddling are needed
            var message = JSON.parse(event.data);

            var row = messageTable.getElementsByTagName("tbody")[0].insertRow(0);
            var cell0 = row.insertCell(0);
            var cell1 = row.insertCell(1);
            var cell2 = row.insertCell(2);
            var cell3 = row.insertCell(3);

            cell0.className = "author-style";
            cell0.innerHTML = message.author;

            cell1.className = "text";
            cell1.innerHTML = message.message;

            cell2.className = "date";
            cell2.innerHTML = message.timestamp;

            cell3.className = "address";
            cell3.innerHTML = message.serviceAddress;

        });

        this.source.onerror = function (e) {
            console.log(e);
            this.close();
        };

    };

    this.stop = function() {
        this.source.close();
    }

}

message = new loadMessages();

/*
 * Register callbacks for starting and stopping the SSE controller.
 */
window.onload = function() {
    message.start();
};
window.onbeforeunload = function() {
    message.stop();
}