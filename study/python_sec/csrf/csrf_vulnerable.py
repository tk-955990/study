#!/home/tk955990/anaconda3/bin/python
# -*- coding: utf-8 -*-

from bottle import route
from bottle import run
from bottle import request
from bottle import response
from bottle import redirect
from bottle import get
import os

USER_ID = 'user1'
os.environ['PASSWORD'] = '123456'


@route('/')
def index():
    html = '<h2> CSRF demo </h2>'
    if isloggedin():
        username = request.get_cookie('sessionid', secret='password')
        html += '</form>'
        html += 'Hello ' + str(username)
        html += '<form action="/changepasswd" method="POST">'
        html += 'Change password: <input type="text" name="password" />'
        html += '<input type="submit" value="update" />'
        return html
    else:
        return html + 'You must login <a href="/login">here.</a>'


@get('/login')
def login():
    html = '<h2> CSRF demo</h2>'
    html += '<form action="/login" method="POST">'
    html += 'User ID: <input type="text" name="user_id" /> <br>'
    html += 'Password: <input type="text" name="password" />'
    html += '<input type="submit" value="login" />'
    html += '</form>'
    return html


@route('/login', method='POST')
def do_login():
    user_id = request.forms.get('user_id')
    password = request.forms.get('password')
    if authenticate(user_id, password):
        response.set_cookie('sessionid', user_id, secret='password')
        return redirect('/')
    else:
        return '<h2> CSRF demo </h2> Login failed.'


def isloggedin():
    cookie = request.get_cookie('sessionid', secret='password')
    return False if cookie is None else True


def authenticate(user_id, password):
    if user_id == USER_ID and password == os.environ['PASSWORD']:
        return True
    else:
        return False


@route('/changepasswd', method="POST")
def change_passwd():
    if isloggedin():
        new_passwd = request.forms.get('password')
        os.environ['PASSWORD'] = new_passwd
        return redirect('/login')
    else:
        html = 'You must login <a href="/login">here.</a>'
        return html


run(host='0.0.0.0', port=8000, debug=True)
