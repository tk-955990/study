#!/home/tk955990/anaconda3/bin/python
# -*- coding: utf-8 -*-

from bottle import route
from bottle import run


@route('/')
def hello():
    html = '<h2>ターゲットのWebサイト</h2>'
    html += '<button type="button" value="button"'
    html += 'onclick="alert(\'商品Aを買いました\')">'
    html += '商品Aを購入する</button>'
    return html


run(host='0.0.0.0', port=8000, debug=True)
