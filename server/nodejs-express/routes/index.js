var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
  res.render('index', { title: 'Occupancy Detection and Visualization' });
});

router.post('/ping', function(req, res) {
  console.log(req.headers);
  res.send('data received');
});

// initial functionality
router.post('/update', function(req, res) {
  console.log(req.headers);
  // receive building_id, room_id, occupied_flag
});

router.get('/latest', function(req, res) {
  console.log(req.headers);
  // return json-encoded values
});

module.exports = router;
