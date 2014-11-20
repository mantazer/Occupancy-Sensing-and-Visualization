from flask import Flask, request, render_template


import json
import threading
import time

app = Flask(__name__)


@app.route('/')
def index():
    list = [{'RoomID': '101', 'RoomLocation': '1st Floor', 'Occupied': "true"}, {'RoomID': '201','RoomLocation': '2nd Floor', 'Occupied': "true"}, {'RoomID': '301','RoomLocation': '3rd Floor', 'Occupied': "false"}]
    user = {'nickname': 'Miguel'}  # fake user
    return render_template('index.html', rooms=list)

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)

