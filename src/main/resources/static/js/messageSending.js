const sendButton = document.getElementById("reply-btn")
const textarea = document.getElementById("comment")
const messages = document.getElementById("conversation")

sendButton.addEventListener('click', (e) => {
    sendMessage();
})

const sendMessage = async () => {
    const url = new URL("http://localhost:8080/api/dialog/send/" + recipient),
        params = {'text':textarea.value};
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
    const message = await fetch(url, {method: 'POST'})
        .then(response => response.json());
    messages.innerHTML +=
        `<div class="row message-body" >
            <div class="col message-main-sender">
                <div class="sender">
                    <div class="message-text">
                        ${textarea.value}
                    </div>
                    <span class="message-time">
                        ${message.date}
                    </span>
                </div>
            </div>
        </div>`
    textarea.value = "";
    stompClient.send("/chat/" + message.dialog.id, {}, JSON.stringify(message.id));
}

let stompClient = null;

function connect() {
    let socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/messages/' + dialog, function(resp) {
            let message = JSON.parse(resp.body);
            if(message.sender.id == recipient) {
                messages.innerHTML +=
                    `<div class="row message-body" >
                        <div class="col message-main-receiver">
                            <div class="receiver">
                                <div class="message-text">
                                    ${message.text}
                                </div>
                                <span class="message-time">
                                    ${message.date}
                                </span>
                            </div>
                        </div>
                    </div>`
            }
        });
    });
}


