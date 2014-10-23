var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
  res.render('index', { title: 'Occupancy Detection and Visualization' });
});

router.post('/ping', function(req, res) {
  console.log(req.headers);
  res.send('data received');
});

module.exports = router;
