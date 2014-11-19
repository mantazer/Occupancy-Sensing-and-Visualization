from db import MongoDB
from flask import Flask, request, render_template
from model import Node

import json
import threading
import time

app = Flask(__name__)
mongodb = MongoDB()

thread = threading.Thread(target=mongodb.find_vacant)
thread.start()

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/ping', methods=['POST'])
def ping():
    if request.method == 'POST':
        node_id = request.headers.get('node-id')
        print 'Incoming ping from node ' + node_id
        incoming_node = Node(node_id)
        success = mongodb.add_or_update(incoming_node)

        if success:
            return '200'
        return '500'

@app.route('/vacant', methods=['GET'])
def vacant():
    if request.method == 'GET':
        vacant_nodes = mongodb.get_vacant()
        return json.dumps(vacant_nodes)
        pass

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)

