#!/home/tk955990/anaconda3/bin/python
#-*- coding: utf-8 -*-

from http.server import HTTPServer
from http.server import SimpleHTTPRequestHandler

ip = '127.0.0.1'
port = 8000

handler = SimpleHTTPRequestHandler
server = HTTPServer((ip, port), handler)

print(f"Stating server ag http://{ip}:{port}")

server.serve_forever()
