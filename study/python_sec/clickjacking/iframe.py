#!/home/tk955990/anaconda3/bin/python
# -*- coding: utf-8 -*-

from bottle import route
from bottle import run


@route('/')
def hello():
    target_url = 'http://localhost:8000'
    html = '<h2>攻撃者のWebサイト</h2>'
    html += '<iframe src="'
    html += target_url
    html += '"></iframe>'
    return html


run(host='0.0.0.0', port=8080, debug=True)
