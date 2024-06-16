#!//home/tk955990/anaconda3/bin/python
#-*- coding: utf-8 -*-

import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

host = '127.0.0.1'
port = 4444

s.bind((host, port))
s.listen(1)

print("Waiting connection ...")

# コネクションとクライアントの情報が返ってくる
connection, addr = s.accept()
print("Connection from: " + str(addr))

while True:
      # クライアントからのデータを受信
      data = connection.recv(1024)

      # クライアントからexitというデータが送られてきたら終了
      if data == b"exit":
          break

      print("Received a message: " + str(data))

      # クライアントにデータを送信 
      connection.send(data)
      print("Sent a message: " + str(data))

# connectionとsocketをクローズ
connection.close()
s.close()
