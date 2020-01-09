const CODE = {
    success: 200,
    failure: -1
};

const SERVER = 'http://localhost:9010';

const URL = {
    login: SERVER + '/access/login',
    logout: SERVER + '/access/logout',
    getNodeAddress: SERVER + '/node/get/best',
    connectNode: SERVER + '/node/connect'
};

const SUBSCRIBE = {
    privateChannel: '/user/private/message',
    groupChannel: '/user/group/message',
    notifyChannel: '/user/notify'
};

const SEND = {
    privateChannel: '/to/private/send',
    groupChannel: '/to/group/send'
};

let globalData = {
    token: '',
    node: {
        id: '',
        address: ''
    }
};

let stompClient = null;

function connect() {
    // Login by HTTP request to obtain token
    const authLogin = {
        username: $('#username').val(),
        password: $('#password').val()
    };
    $.ajax({
        type: "POST",
        url: URL.login,
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(authLogin),
    }).done(function (data) {
        if (data.code === CODE.success) {
            globalData.token = data.data;
            console.log('Login successfully with token:');
            console.log(globalData.token);
            getNodeAddress();
        } else {
            alert(data.message);
        }
    });

}

function getNodeAddress() {
    $.ajax({
        type: "GET",
        url: URL.getNodeAddress,
        dataType: 'json',
        contentType: 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", globalData.token);
        },
    }).done(function (data) {
        if (data.code === CODE.success) {
            globalData.node = data.data;
            console.log('Obtain node successfully:');
            console.log(globalData.node);
            connectNode();
        } else {
            alert(data.message);
        }
    });
}

function connectNode() {
    const socket = new SockJS(globalData.node.address);
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        // Parse session id
        const urlSlice = stompClient.ws._transport.url.split('/');
        const sessionId = urlSlice[urlSlice.length - 2];

        // Connect the node
        $.ajax({
            type: "POST",
            url: URL.connectNode + '/' + globalData.node.id + '/' + sessionId,
            dataType: 'json',
            contentType: 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", globalData.token);
            },
        }).done(function (data) {
            if (data.code === CODE.success) {
                $('#my-uuid').val(data.data.uuid);
                setConnected(true);
                console.log('Connected');
                subscribeChannel();
            } else {
                alert(data.message);
                disconnect();
            }
        });
    });
}

function subscribeChannel() {
    // Subscribe private chat channel
    stompClient.subscribe(SUBSCRIBE.privateChannel, function (data) {
        data = JSON.parse(data.body);
        if (data.code === CODE.success) {
            const message = data.data;
            showRecord(message)
        } else {
            alert(data.message)
        }
    });

    // Subscribe group chat channel
    stompClient.subscribe(SUBSCRIBE.groupChannel, function (data) {
        data = JSON.parse(data.body);
        if (data.code === CODE.success) {
            const message = data.data;
            showRecord(message)
        } else {
            alert(data.message)
        }
    });

    // Subscribe notify channel
    stompClient.subscribe(SUBSCRIBE.notifyChannel, function (data) {
        data = JSON.parse(data.body);
        if (data.code === CODE.success) {
            const message = data.data;
            showRecord(message)
        } else {
            alert(data.message)
        }
    });
}

function disconnect() {
    logout();
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function logout() {
    $.ajax({
        type: "POST",
        url: URL.logout,
        dataType: 'json',
        contentType: 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", globalData.token);
        },
    }).done(function (data) {
        if (data.code === CODE.success) {
            console.log('Logout successfully.');
        } else {
            alert(data.message);
        }
    });
}

function sendPrivateMessage() {
    const message = {
        sender: $("#my-uuid").val(),
        receiver: $("#target-uuid").val(),
        content: $("#private-message").val()
    };
    stompClient.send(SEND.privateChannel, {}, JSON.stringify(message));
}

function sendGroupMessage() {
    const message = {
        sender: $("#my-uuid").val(),
        receiver: $("#group-uuid").val(),
        content: $("#group-message").val()
    };
    stompClient.send(SEND.groupChannel, {}, JSON.stringify(message));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send-private").click(function () {
        sendPrivateMessage();
    });
    $("#send-group").click(function () {
        sendGroupMessage();
    });
});

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#record").html("");
}

function showRecord(message) {
    const record = "<tr>" +
        "<td>" + message.sender + "</td>" +
        "<td>" + message.receiver + "</td>" +
        "<td>" + message.content + "</td>" +
        "<td>" + message.timestamp + "</td>" +
        "</tr>";
    $("#record").append(record);
}