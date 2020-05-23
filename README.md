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

- EC2 で Docker で動かそう！！！
```
$ sudo yum update
$ sudo yum install -y docker
$ sudo service docker start
$ sudo systemctl enable docker
( $ sudo chkconfig docker on )

$ sudo usermod -a -G docker ec2-user

$ curl -L "https://github.com/docker/compose/releases/download/1.9.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
$ sudo chmod +x /usr/local/bin/docker-compose


$ sudo chkconfig cloud-init on

sudo vim /var/lib/cloud/scripts/per-boot/start_container.sh
--------------------------------------------------------
#!/bin/sh

cd /home/ec2-user
/usr/local/bin/docker-compose up -d
--------------------------------------------------------
```


`https://hacknote.jp/archives/6107/` 様
- docker-compose 自動起動
```
amazon linuxの起動時にdockerのコンテナを自動起動する
amazon linuxを起動したときにdockerのコンテナを自動起動するように設定する方法です。
まずdocker自体の起動を有効にしておきます。

$ chkconfig docker on

次に以下のようにスクリプトを配置します。以下はtestというコンテナを起動する場合の例です。

vim /var/lib/cloud/scripts/per-boot/start_container.sh
--------------------------------------------------------
#!/bin/sh

cd /home/ec2-user
/usr/local/bin/docker-compose up -d
--------------------------------------------------------
/var/lib/cloud/scripts/per-boot/に置いたスクリプトは起動毎にcloud-initが自動で実行してくれます。
```
備忘: rootユーザで実行するので `/usr/local/bin/docker-compose` で指定しておく。

- `docker-compose` でログ見る
```
$ docker-compose logs -f
```

```java
    public enum KadonoFamilly {
        ANIKI,
        WATASHI,
        OYAJI,
        OFUKURO,
        TORAJIRO,
        SHIMA;
        static {
            ANIKI.parents = asList(OYAJI, OFUKURO);
            WATASHI.parents = asList(OYAJI, OFUKURO);
            OYAJI.parents = emptyList();
            OFUKURO.parents = emptyList();
            TORAJIRO.parents = singletonList(WATASHI);
            SHIMA.parents = singletonList(WATASHI);
        }
        private List<KadonoFamilly> parents;
        public List<KadonoFamilly> getParents() {
            return parents;
        }
    }
```
```
    @Test
    public void hoge() {
        System.out.println(KadonoFamilly.ANIKI.parents); // 出力: [OYAJI, OFUKURO]
    }
```

```
        Runnable runnable1 = () -> userEmailCoordinator.create(1008L, "kadono@gamil.com");
        Runnable runnable2 = () -> userEmailCoordinator.create(1008L, "kadono2@gamil.com");
        new Thread(runnable1).start();
        new Thread(runnable2).start();
        try {
            Thread.sleep(10000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
```

## Linuxのタイムゾーン設定
`https://qiita.com/azusanakano/items/b39bd22504313884a7c3` 様参考
```
# 一応、バックアップを取ります
cp /etc/sysconfig/clock /etc/sysconfig/clock.org

# viなどで編集してもよし
echo -e 'ZONE="Asia/Tokyo"\nUTC=false' > /etc/sysconfig/clock

# 一応、バックアップを取ります
cp /etc/localtime /etc/localtime.org

# timezoneファイル差し替え
ln -sf  /usr/share/zoneinfo/Asia/Tokyo /etc/localtime
```

## Amazon Linux のVolume を拡張した話
```
# lsblk コマンドで、容量が増えていることを確認
$ lsblk

# パーティションの拡張
$ sudo growpart /dev/xvda 1

# ファイルシステムの拡張
# $ sudo resize2fs /dev/xvda1 出来ず
# XFSではresize2fs の代わりに xfs_growfs を使えばOK
$ sudo xfs_growfs /dev/centos/root

# 確認
$ lsblk
$ df -h
