# chat
spring-boot-starter-websocket


## Hot Reload
1. intelliJの設定(Shift2回連打)で、Build project automaticallyと打って出てくるもので、`Build project automatically`を有効にしてapply
2. Help > Find Action... から、"Registry" って打って出てくるもので `compiler.automake.allow.when.app.running` を有効にする
3. Edit ConfigurationsでSpringbootの列で下記の設定を入れる（デフォルトではどちらもDo Nothingとなっている）
   - On Update action: Update trriger file
   - On frame deactivation: Update classes and resources
   
   
   
   
 ## https://spring-boot-camp.readthedocs.io/ja/latest/06-STOMP.html 様のサンプル
 
 ```
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello STOMP</title>
</head>
<body>
<div>
    <button id="connect">Connect</button>
    <button id="disconnect" disabled="disabled">Disconnect</button>
</div>
<div>
    <input type="text" id="name" placeholder="Your Name">
    <button id="send" disabled="disabled">Send</button>
    <div id="response"></div>
</div>
</body>
<script src="stomp.js"></script>
<script type="text/javascript">
    /**
     * 初期化処理
     */
    var HelloStomp = function () {
        this.connectButton = document.getElementById('connect');
        this.disconnectButton = document.getElementById('disconnect');
        this.sendButton = document.getElementById('send');

        // イベントハンドラの登録
        this.connectButton.addEventListener('click', this.connect.bind(this));
        this.disconnectButton.addEventListener('click', this.disconnect.bind(this));
        this.sendButton.addEventListener('click', this.sendName.bind(this));
    };

    /**
     * エンドポイントへの接続処理
     */
    HelloStomp.prototype.connect = function () {
        var socket = new WebSocket('ws://' + location.host + '/endpoint'); // エンドポイントのURL
        this.stompClient = Stomp.over(socket); // WebSocketを使ったStompクライアントを作成
        this.stompClient.connect({}, this.onConnected.bind(this)); // エンドポイントに接続し、接続した際のコールバックを登録
    };

    /**
     * エンドポイントへ接続したときの処理
     */
    HelloStomp.prototype.onConnected = function (frame) {
        console.log('Connected: ' + frame);
        // 宛先が'/topic/greetings'のメッセージを購読し、コールバック処理を登録
        this.stompClient.subscribe('/topic/greetings', this.onSubscribeGreeting.bind(this));
        this.setConnected(true);
    };

    /**
     * 宛先'/topic/greetings'なメッセージを受信したときの処理
     */
    HelloStomp.prototype.onSubscribeGreeting = function (message) {
        var response = document.getElementById('response');
        var p = document.createElement('p');
        p.appendChild(document.createTextNode(message.body));
        response.insertBefore(p, response.children[0]);
    };

    /**
     * 宛先'/app/greet'へのメッセージ送信処理
     */
    HelloStomp.prototype.sendName = function () {
        var name = document.getElementById('name').value;
        this.stompClient.send('/app/greet', {}, name); // 宛先'/app/greet'へメッセージを送信
    };

    /**
     * 接続切断処理
     */
    HelloStomp.prototype.disconnect = function () {
        if (this.stompClient) {
            this.stompClient.disconnect();
            this.stompClient = null;
        }
        this.setConnected(false);
    };

    /**
     * ボタン表示の切り替え
     */
    HelloStomp.prototype.setConnected = function (connected) {
        this.connectButton.disabled = connected;
        this.disconnectButton.disabled = !connected;
        this.sendButton.disabled = !connected;
    };

    new HelloStomp();
</script>
</html>
```