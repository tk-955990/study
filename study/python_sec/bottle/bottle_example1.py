#!/home/tk955990/anaconda3/bin/python
#-*- coding: utf-8 -*-

from bottle import route
from bottle import run

@route('/hello')
def index():
    html ='''
    <html>
    <title>bottle_test</title>
    <h1>Hello World!</h1>
    </html>
    '''

    return html

run(host='0.0.0.0', port=8080)
