#!/home/tk955990/anaconda3/bin/python
# -*- coding: utf-8 -*-

from bottle import route
from bottle import run
from bottle import request
import html


@route('/')
def hello(user=''):
    username = request.query.get('user')
    username = '' if username is None else username
    username = html.escape(username)

    escaped_html = "<h2> Hello {name} </h2>".format(name=username)

    return escaped_html


run(host='0.0.0.0', port=8080, debug=True)
