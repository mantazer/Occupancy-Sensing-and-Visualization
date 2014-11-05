from pymongo import MongoClient
import requests
import time

class MongoDB:
    client = MongoClient('localhost', 27017)
    db = client.db
    node_collection = db.node_collection

    def add_if_not_exists(self, node):
        pass

