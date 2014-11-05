from db import MongoDB
from flask import Flask, request
from model import Node

import json
import threading
import time

app = Flask(__name__)

@app.route('/')
def index():
    return 'Occupancy Detection and Visualization'

@app.route('/ping', methods=['POST'])
def ping():
    if request.method == 'POST':
        print request.headers
        return 'data received'

if __name__ == '__main__':
    app.run(host='0.0.0.0')
