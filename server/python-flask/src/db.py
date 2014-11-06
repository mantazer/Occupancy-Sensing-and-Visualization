from pymongo import MongoClient
import requests
import time

class MongoDB:
    client = MongoClient('localhost', 27017)
    db = client.db
    node_collection = db.node_collection

    def add_if_not_exists(self, node):
        if self.node_collection.find_one({'cc3200_id': node.node_id}) is None:
            node_data = {'node_id': node.node_id, 'occupied': node.occupied}
            node_id = self.node_collection.insert(node_data)
            return node_id
        else:
            return None
    
    def find_vacant(self):
        pass

