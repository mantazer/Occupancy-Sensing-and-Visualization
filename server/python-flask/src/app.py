from db import MongoDB
from flask import Flask, request, render_template
from model import Node

import json
import threading
import time
import requests

app = Flask(__name__)
mongodb = MongoDB()

thread = threading.Thread(target=mongodb.find_vacant)
thread.start()

@app.route('/')
def index():
    list = mongodb.get_all()
    return render_template('index.html', rooms=list.get('nodes'))

@app.route('/ping', methods=['POST'])
def ping():
    if request.method == 'POST':
        node_id = request.headers.get('node-id')
        node_floor = request.headers.get('node-floor')
        print 'Incoming ping from node ' + node_id
        incoming_node = Node(node_id, node_floor)
        success = mongodb.add_or_update(incoming_node)

        if success:
            return '200'
        return '500'

@app.route('/vacant', methods=['GET'])
def vacant():
    if request.method == 'GET':
        vacant_nodes = mongodb.get_vacant()
        return json.dumps(vacant_nodes)

@app.route('/all', methods=['GET'])
def all():
    if request.method == 'GET':
        all_nodes = mongodb.get_all()
        return json.dumps(all_nodes)

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)

