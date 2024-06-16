#!/home/tk955990/anaconda3/bin/python
#-*- coding: utf-8 -*-

import requests

url = 'http://localhost:8000'
response = requests.get(url)
print(response.text)
