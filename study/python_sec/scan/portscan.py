#!/home/tk955990/anaconda3/bin/python
#-*- coding: utf-8 -*-

import socket, sys

ip = sys.argv[1]
ports = range(1, 10000)

for port in ports:
    s = socket.socket()
    ret = s.connect_ex((ip, port))

    if ret == 0:
        print(str(port) + " open")
