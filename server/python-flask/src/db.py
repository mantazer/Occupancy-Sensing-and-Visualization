from pymongo import MongoClient
import requests
import time

class MongoDB:
    client = MongoClient('localhost', 27017)
    db = client.db
    node_collection = db.node_collection

    def add_or_update(self, node):
        if self.node_collection.find_one({'node_id': node.node_id}) is None:
            node_data = {'node_id': node.node_id, 'last_triggered': time.time(),
            'is_vacant': 0}
            node_id = self.node_collection.insert(node_data)
            return True
        else:
            write_ack = self.node_collection.update({'node_id': node.node_id}, {'$set':
            {'last_triggered': time.time(), 'is_vacant': 0}}, upsert=False)
            if write_ack:
                return True
            return False
        return False


    def find_vacant(self):
        pass

