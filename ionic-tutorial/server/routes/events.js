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
        eventUsersIds = req.body.friendsById,
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
    // Converting int array into string
    console.log(eventUsersIds);
    eventUsersIds = JSON.stringify(eventUsersIds);
    console.log(eventUsersIds);
    let placeholders = Array(17).join("?");
    placeholders = placeholders.split("").join(',');

    let sql = 'INSERT INTO events(adminUserId, intCreationDate, intDate, creationDate,' +
              'date, maxUsers, currentUsers, category, description, price,' +
              'friendsById, isPrivate, eventImage, placeID, latitude, longitude) VALUES (' + placeholders + ')';

    db.run(sql, [adminUserId,creationDate,scheduledDate,creationDateText,scheduledDateText,
                 maxUsers, currentUsers, category, description, price, eventUsersIds, isPrivate,
                 eventImage, placeId, lat, longitude], function(err) {
        if (err) {
          return console.log(err.message);
        }
        // get the last insert id
        res.send({"id":this.lastID});
    });
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
            friendsById = JSON.parse(row.friendsById);
            isPrivateInt = row.isPrivate;
            if (isPrivateInt == 1)
                isPrivateBool = true;
            else
                isPrivateBool = false;

            toSend.push(row);
            toSend[toSend.length - 1].friendsById = friendsById;
            toSend[toSend.length - 1].isPrivate = isPrivateBool;
          });
        console.log(toSend);
        res.send(toSend);
        /*res.send([{"adminUserId":0,"category":"Football","creationDate":"Jan 24, 2018 1:49:39 AM","currentUsers":0,"date":"Feb 24, 3918 1:48:39 AM","description":"great game! please dont be late and if you have a ball bring it","eventImage":"No image","friendsById":[1,2,3,4,5,-1],"id":0,"intCreationDate":1516754979,"intDate":1348035175,"isPrivate":false,"latitude":31.269231100000006,"longitude":34.7899561,"maxUsers":12,"placeID":"ChIJv2SgkolmAhURjzy_BwgSru0","price":0},
                  {"adminUserId":0,"category":"Football","creationDate":"Jan 24, 2018 1:49:39 AM","currentUsers":4,"date":"Feb 24, 3918 1:48:39 AM","description":"great game! please dont be late and if you have a ball bring it","eventImage":"No image","friendsById":[1,2,3,4,5,-1],"id":0,"intCreationDate":1516754979,"intDate":1348035175,"isPrivate":false,"latitude":31.269231100000006,"longitude":34.7899561,"maxUsers":4,"placeID":"ChIJv2SgkolmAhURjzy_BwgSru0","price":2},
                  {"adminUserId":0,"category":"Football","creationDate":"Jan 24, 2018 1:49:39 AM","currentUsers":9,"date":"Feb 24, 3918 1:48:39 AM","description":"great game! please dont be late and if you have a ball bring it","eventImage":"No image","friendsById":[1,2,3,4,5,-1],"id":0,"intCreationDate":1516754979,"intDate":1348035175,"isPrivate":false,"latitude":31.269231100000006,"longitude":34.7899561,"maxUsers":98,"placeID":"ChIJv2SgkolmAhURjzy_BwgSru0","price":70}]);*/
        });
    });
};

exports.updateAttend = function (req, res, next) {
    var userid =  parseInt(req.params.userid),
        eventid = parseInt(req.params.eventid);
    console.log("userid = " + userid + ", eventid = " + eventid);

    db.serialize(() => {
        var newfriendsById = [], attendingEventsIds = [],toSend = {};

        db.get('SELECT friendsById, currentUsers, maxUsers FROM events WHERE id = ?', [eventid], (err, row) => {
            if (err) {
                return console.error(err.message);
            }
            if (row){
                if (row.currentUsers + 1 <= row.maxUsers){
                    newfriendsById = JSON.parse(row.friendsById);
                    console.log("friendsById fresh from db - ");
                    console.log(newfriendsById);
                    newfriendsById.push(userid);
                    console.log("original database friendsById: ");
                    console.log(row.friendsById);
                    newfriendsById = JSON.stringify(newfriendsById);
                }
                else res.send("Can't join a full event!");
            }
            else
                res.send("invalid event id");
        })
        .get('SELECT attendingEventsIds FROM users WHERE userId = ?', [userid], (err, row) => {
            if (err) {
                return console.error(err.message);
            }
            if (row){
                attendingEventsIds = JSON.parse(row.attendingEventsIds);
                attendingEventsIds.push(eventid);
                attendingEventsIds = JSON.stringify(attendingEventsIds);
            }
            else
                res.send("invalid user id");
        })

        .run('UPDATE users SET attendingEventsIds = ? WHERE userId = ?',
            [attendingEventsIds,userid], function(err) {
            if (err) {
              return console.error(err.message);
            }
            console.log('Row(s) updated: ${this.changes}');
            console.log(attendingEventsIds);
            console.log(JSON.stringify(attendingEventsIds));
        })
        .run('UPDATE events SET friendsById = ?, currentUsers = currentUsers + 1 WHERE id = ?',
             [newfriendsById,eventid], function(err) {
             if (err) {
               return console.error(err.message);
             }
             console.log("Here is the stringified friendsById: ")
             console.log(JSON.stringify(newfriendsById));
             console.log("friendById not stringified:");
             console.log(newfriendsById);
         })
         .get('SELECT * FROM events WHERE id = ?',[eventid], (err, row) => {
             if (err) {
                 throw err;
             }

             toSend = row;
             friendsById = JSON.parse(row.friendsById);

             console.log(row);

             isPrivateInt = row.isPrivate;
             if (isPrivateInt == 1)
                 isPrivateBool = true;
             else
                 isPrivateBool = false;

             toSend.friendsById = friendsById;
             toSend.isPrivate = isPrivateBool;
             res.send(toSend);
             console.log("Event has been sent");
             console.log(toSend);
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