// --------------------- Original Networking code ------------------------
var express = require('express'),
    bodyParser      = require('body-parser'),
    methodOverride  = require('method-override'),
    events        = require('./routes/events'),
    users         = require('./routes/users'),
    stormpath = require('express-stormpath'),
    app = express();


app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

app.use(methodOverride());      // simulate DELETE and PUT

// CORS (Cross-Origin Resource Sharing) headers to support Cross-site HTTP requests
app.all('*', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    next();
});

app.get('/events', events.findAll);         //Retrieve all events
app.post('/addRow', events.insertRow);      //Insert new event
app.post('/register', users.addUser);      //Insert new user
app.post('/login', users.loginProcess); //Check user login and return user details
app.get('/joinEvent/:userid/:eventid',events.updateAttend);
app.get('/eventsById/:userid',events.eventsById);

app.set('port', process.env.PORT || 5000);

app.listen(app.get('port'), function () {
    console.log('Express server listening on port ' + app.get('port'));
});
