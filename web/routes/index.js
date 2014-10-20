var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res) {
  res.render('index', { title: 'Occupancy Sensing and Visualization' });
});

router.get('/g', function(req, res) {
  res.send('get response');
});

router.post('/p', function(req, res) {
  res.send('post response');
});

// router.post('/updateoccupancy', function(req, res) {

// });

// router.get('/getoccupancy', function(req, res) {

// });

module.exports = router;
