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
            if node_id:
                return True
            return False
        else:
            write_ack = self.node_collection.update({'node_id': node.node_id}, {'$set':
            {'last_triggered': time.time(), 'is_vacant': 0}}, upsert=False)
            if write_ack:
                return True
            return False
        return False

    def find_vacant(self):
        while True:
            nodes = self.node_collection.find()
            for node in nodes:
                node_id = node.get('node_id')
                last_triggered = node.get('last_triggered')
                is_vacant = node.get('is_vacant')
                
                if time.time() - float(last_triggered) > 10:
                    if is_vacant == 0:
                        print '{} is vacant'.format(node_id)
                        write_ack = self.node_collection.update({'node_id':
                        node_id}, {'$set': {'is_vacant': 1}}, upsert=False)

            time.sleep(5)

