#!//home/tk955990/anaconda3/bin/python
#-*- coding: utf-8 -*-

import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

host = '127.0.0.1'
port = 4444

server = (host, port)
s.connect(server)

msg = ''
while msg != 'exit':
    # 標準入力からデータを取得
    msg = input('->')

    # サーバにデータを送信
    s.send(msg.encode())

    # サーバからのデータを受信
    data = s.recv(1024)
    
    # サーバから受信したデータを出力
    print('Received from sever: ' + str(data))

s.close()
