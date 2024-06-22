import socket  # ソケット通信を扱うモジュールをインポート
import threading  # スレッドを扱うモジュールをインポート

# サーバーがバインドするIPアドレスとポートを定義
IP = '0.0.0.0'  # すべてのネットワークインターフェイスで接続を受け入れる
PORT = 9998  # サーバーがリッスンするポート


def main():
    # サーバーソケットを作成
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # ソケットをIPアドレスとポートにバインド
    server.bind((IP, PORT))

    # ソケットをリッスン状態に設定（接続待ちのキューの最大数を指定）
    server.listen(5)
    print(f'[*] Listening on {IP}:{PORT}')

    while True:
        # 新しい接続を受け入れる
        client, address = server.accept()

        # addressタプルにはクライアントのIPアドレスとポート番号が含まれている
        print(f'[*] Accepted connection from {address[0]}:{address[1]}')

        # 新しいスレッドを作成し、クライアントのリクエストを処理
        client_handler = threading.Thread(target=handle_client, args=(client,))

        # スレッドを開始
        client_handler.start()


def handle_client(client_socket):
    # クライアントソケットをコンテキストマネージャーとして扱う
    with client_socket as sock:
        # クライアントからデータを受信（最大1024バイト）
        request = sock.recv(1024)

        # 受信したデータをデコードして表示
        print(f'[*] Received: {request.decode("utf-8")}')

        # クライアントにACK応答を送信
        sock.send(b'ACK')


# スクリプトが直接実行された場合にのみmain()を実行
if __name__ == '__main__':
    main()
