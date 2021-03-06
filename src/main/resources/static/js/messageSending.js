const sendButton = document.getElementById("reply-btn")
const textarea = document.getElementById("comment")
const conversation = document.getElementById("conversation")
const fileInput = document.getElementById("msg-file");
const fileName =  document.getElementById('file-name');
let messages = document.getElementsByClassName('message-body');
let stompClient;
sendButton.addEventListener('click', (e) => {
    sendMessage();
});

fileInput.addEventListener('change', (e) => {
    fileName.innerText = fileInput.files[0].name + "      ";
});

addFileLink();

const sendMessage = async () => {
    if(textarea.value != "" || fileName.innerText != "") {
        const formDate = new FormData();
        formDate.append('file', fileInput.files[0]);
        const url = new URL("http://localhost:8080/api/dialog/send/" + recipient),
            params = {'text': textarea.value};
        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
        const message = await fetch(url, {method: 'POST', body: formDate})
            .then(response => response.json());
        if (message.fileName != null) {
            conversation.innerHTML +=
                `<div class="row message-body" >
                <div class="col message-main-sender">
                    <div class="sender">
                        <div class="message-text">
                            ${message.text}
                        </div>
                        <span class="message-footnote">
                            ${message.date}
                        </span>
                        <span class="message-footnote file-ref">
                            ${message.originalFileName}
                        </span>
                        <span class="server-file-name" hidden>${message.fileName}</span>
                    </div>
                </div>
            </div>`
        } else {
            conversation.innerHTML +=
                `<div class="row message-body" >
                <div class="col message-main-sender">
                    <div class="sender">
                        <div class="message-text">
                            ${message.text}
                        </div>
                        <span class="message-footnote">
                            ${message.date}
                        </span>
                    </div>
                </div>
            </div>`
        }
        addFileLink()
        textarea.value = "";
        stompClient.send("/chat/" + message.dialog.id, {}, JSON.stringify(message.id));
        fileInput.value = "";
        fileName.innerText = "";
    }
}

function connect() {
    let socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/messages/' + dialog, function(resp) {
            let message = JSON.parse(resp.body);
            if(message.sender.id == recipient) {
                if(message.fileName != null) {
                    conversation.innerHTML +=
                        `<div class="row message-body" >
                            <div class="col message-main-receiver">
                                <div class="receiver">
                                    <div class="message-text">
                                        ${message.text}
                                    </div>
                                    <span class="message-footnote">
                                        ${message.date}
                                    </span>
                                    <span class="message-footnote file-ref">
                                        ${message.originalFileName}
                                    </span>
                                    <span class="server-file-name" hidden>${message.fileName}</span>
                                </div>
                            </div>
                        </div>`
                } else {
                    conversation.innerHTML +=
                        `<div class="row message-body" >
                            <div class="col message-main-receiver">
                                <div class="receiver">
                                    <div class="message-text">
                                        ${message.text}
                                    </div>
                                    <span class="message-footnote">
                                        ${message.date}
                                    </span>
                                </div>
                            </div>
                        </div>`
                }
                addFileLink();
            }
        });
    });
}

const downloadFile = async (fileName, originalName) => {
    const url = new URL("http://localhost:8080/api/dialog/file/" + fileName);
    await fetch(url)
        .then(response => response.blob())
        .then(function (blob) {
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.href = url;
            a.download = originalName;
            document.body.appendChild(a);
            a.click();
            a.remove();
        });
};

function addFileLink() {
    for(let message of messages) {
        let link = message.querySelector('.file-ref');
        if(link != null) {
            link.addEventListener('click', () => {
                downloadFile(message.querySelector('.server-file-name').innerText, link.innerText);
            });
        }
    }
}

window.onload = function() {
    textarea.addEventListener('input', () => {
        sendButton.className = 'btn btn-primary col-sm-1';
    });
    fileInput.addEventListener('change', () => {
        sendButton.className = 'btn btn-primary col-sm-1';
    });
    connect();
}