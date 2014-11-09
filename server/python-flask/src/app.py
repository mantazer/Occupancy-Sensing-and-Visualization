# TODO: Add one-time registration logic

from db import MongoDB
from flask import Flask, request
from model import Node

import json
import threading
import time

app = Flask(__name__)
mongodb = MongoDB()

@app.route('/')
def index():
    return render_template('MainPage.html')

@app.route('/ping', methods=['POST'])
def ping():
    if request.method == 'POST':
        print request.headers

        node_id = request.headers.get('node-id')
        incoming_node = Node(node_id)

        mongodb.add_if_not_exists(incoming_node)

        return 'data received'

if __name__ == '__main__':
    app.run(host='0.0.0.0')

