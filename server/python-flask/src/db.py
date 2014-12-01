from pymongo import MongoClient
import requests
import time

class MongoDB:
    client = MongoClient('localhost', 27017)
    db = client.db
    node_collection = db.node_collection

    def add_or_update(self, node):
        if self.node_collection.find_one({'node_id': node.node_id}) is None:
            node_data = {'node_id': node.node_id, 'node_floor': node.node_floor, 'last_triggered': time.time(),
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

    def get_vacant(self):
        vacant_nodes = {}
        id_list = []
        nodes = self.node_collection.find()
        for node in nodes:
            if node.get('is_vacant') == 1:
                node_dict = {}
                node_dict['node-id'] = node.get('node_id')
                node_dict['node-floor'] = node.get('node_floor')
                id_list.append(node_dict)
        vacant_nodes['vacant'] = id_list
        return vacant_nodes

    def get_all(self):
        all_nodes = {}
        node_list = []
        nodes = self.node_collection.find()
        for node in nodes:
            node_dict = {}
            node_dict['nodeId'] = node.get('node_id')
            node_dict['nodeFloor'] = node.get('node_floor')
            node_dict['vacant'] = node.get('is_vacant')
            node_list.append(node_dict)
        all_nodes['nodes'] = sorted(node_list)
        return all_nodes

