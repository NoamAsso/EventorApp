// Our database
const sqlite3 = require('sqlite3').verbose();
let db = new sqlite3.Database('./db/eventorDB.db');


// insert one row into the langs table
exports.insertRow = function (req, res, next) {
    console.log("Server inserts new row to db");

//Missing - event id (The server gives the client the value)
    var adminUserId = req.body.adminUserId,
        category = req.body.category,
        creationDateText = req.body.creationDate,
        scheduledDateText = req.body.date,
        creationDate = req.body.intCreationDate,
        scheduledDate = req.body.intDate,
        maxUsers = req.body.maxUsers,
        currentUsers = req.body.currentUsers,
        description = req.body.description,
        price = req.body.price,
        eventImage = req.body.eventImage,
        placeId = req.body.placeID,
        lat = req.body.latitude,
        longitude = req.body.longitude,
        isPrivate;
    // Converting boolean to int
    if (req.body.isPrivate)
        isPrivate = 1;
    else
        isPrivate = 0;

/*    // Converting image string to base64 (blob)
    eventImage = new Buffer(eventImage).toString('base64');
    console.log(typeof(eventImage));
*/
    console.log(typeof(scheduledDateText));
    let placeholders = Array(16).join("?");
    placeholders = placeholders.split("").join(',');

    let sqlOfEvents = 'INSERT INTO events(adminUserId, intCreationDate, intDate, creationDate,' +
              'date, maxUsers, currentUsers, category, description, price,' +
              'isPrivate, eventImage, placeID, latitude, longitude) VALUES (' + placeholders + ')';
    let sqlOfConnections = 'INSERT INTO connections(userId, eventId) VALUES(?,?)';
    db.serialize(() => {
        var lastEventID;
        //Update Events table
        db.run(sqlOfEvents, [adminUserId,creationDate,scheduledDate,creationDateText,scheduledDateText,
               maxUsers, currentUsers, category, description, price, isPrivate,
               eventImage, placeId, lat, longitude], function(err) {

            if (err) {
              return console.log(err.message);
            }
            // get the last insert id
            lastEventID = this.lastID;
            //Update connections table
            db.run(sqlOfConnections, [adminUserId, lastEventID], function(err) {
                 if (err) {
                   return console.log(err.message);
                 }
                 console.log("adminUserId: ");
                 console.log(adminUserId);
                 console.log("lastEventId: ");
                 console.log(lastEventID);

                 res.send({"id":lastEventID});
            });
        })
    }); //end serialize
};

exports.findAll = function (req, res, next) {
    db.serialize(() => {
        console.log("Got request for all db, sending...");
        toSend = [];

        db.all('SELECT * FROM events', (err, rows) => {
            if (err) {
                throw err;
            }
        //console.log(rows);
        rows.forEach((row) => {
        //    console.log(row.eventId);
            isPrivateInt = row.isPrivate;
            if (isPrivateInt == 1)
                isPrivateBool = true;
            else
                isPrivateBool = false;

            toSend.push(row);
            toSend[toSend.length - 1].isPrivate = isPrivateBool;
          });
        console.log(toSend);
        res.send(toSend);
        });
    });
};

exports.updateAttend = function (req, res, next) {
    var userid =  parseInt(req.params.userid),
        eventid = parseInt(req.params.eventid);
    var toSend = [];
    console.log("userid = " + userid + ", eventid = " + eventid);

    db.serialize(() => {
        sqlUpdateEvents = 'UPDATE events SET currentUsers = currentUsers + 1 WHERE id = ?';
        sqlInsertConnections = 'INSERT INTO connections(userId, eventId) VALUES(?,?)';
        var event={};

        db.get('SELECT * FROM events WHERE id=?',[userid], (err,row) => {
            if (err) {
                return console.log(err.message);
            }
            if((row.currentUsers  + 1) > row.maxUsers){
                console.log("Can't add users to a full event!");
                res.send("Can't add users to a full event!");
            }
            else {
                event = row;
            }
            db.run(sqlUpdateEvents, [eventid], function(err) {
               if (err) {
                 return console.log(err.message);
               }
               console.log("events table was updated (currentUsers)");
               db.run(sqlInsertConnections, [userid,eventid], function(err) {
                    if (err) {
                      return console.log(err.message);
                    }
                    console.log("connections table was updated, sending back the event's user list");
                    db.all('SELECT userId FROM connections WHERE eventId = ?',[eventid], (err,rows) => {
                        if (err) {
                          return console.log(err.message);
                        }
                        console.log(rows);
                        res.send(rows);
                    })
               });
            });
        });
    });
}

/*
exports.findById = function (req, res, next) {
    var id = req.params.id;
    res.send(events[id]);
};*/

exports.findCloseBy = function (req, res, next) {
    // Pseudo code
    //Find all rows that are located closer than the distance in the url request
    // meaning: var = req.params.distance (?)
    //          refPlaceId = req.params.placeId
    //          for each row in database, check its placeId against the refPlaceId with google API
    //          select only these that fulfill that condition
};