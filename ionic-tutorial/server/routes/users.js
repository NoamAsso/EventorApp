const sqlite3 = require('sqlite3').verbose();
let db = new sqlite3.Database('./db/eventorDB.db');

// Add new user
exports.addUser = function (req, res, next) {
    var valid = true;
    console.log("Server inserts new User to db");
    //Missing - event id (The server gives the client the value)
    var userName = req.body.userName,
        password = req.body.password,
        age = req.body.age,
        mail = req.body.mail,
        phoneNum = req.body.phoneNum,
        sex = req.body.sex,
        attendingEventsIds = req.body.attendingEventsIds,
        createdEventsIds = req.body.createdEventsIds,
        friendsIds = req.body.friendsIds;

    attendingEventsIds = JSON.stringify(attendingEventsIds);
    createdEventsIds = JSON.stringify(createdEventsIds);
    friendsIds = JSON.stringify(friendsIds);

    let placeholders = Array(10).join("?");
    placeholders = placeholders.split("").join(',');


    let sql2 = 'INSERT INTO users(userName, password, age, mail, phoneNum, sex, ' +
    'attendingEventsIds, createdEventsIds, friendsIds) VALUES (' + placeholders + ')';
    db.serialize(() => {
        let sql1 = 'SELECT userName FROM users WHERE userName = ?';

        // first row only
        db.get(sql1, [req.body.userName], (err, row) => {
            if (err) {
                return console.error(err.message);
            }
            if (row)
                valid = false;
            else
                console.log("valid username");
        })
        .run(sql2, [userName, password, age, mail, phoneNum, sex, attendingEventsIds,
                     createdEventsIds, friendsIds], function(err) {
            if (err) {
              return console.log(err.message);
            }
            if (valid)
            // get the last insert id
                res.send({"userId":this.lastID});
            else {
                res.send("3"); //There is already a user with this username
            }
        });
    });
};

//TODO: 1. when registering, check if username already exists
//TODO: 2. find user by name. if not found return 1. if password wrong return 2. otherwise return user

exports.findByUsername = function (req, res, next) {
    console.log("user is trying to log in");
    console.log(req.body);

    let sql = 'SELECT * FROM users WHERE userName = ?';
    // first row only
    db.get(sql, [req.body.userName], (err, row) => {
      if (err) {
        return console.error(err.message);
      }


      console.log("row = " + JSON.stringify(row));

      if(row){
        if(row.password != req.body.password){
            toSend = "2";
        }
        else {
            attendingEventsIds = JSON.parse(row.attendingEventsIds);
            createdEventsIds = JSON.parse(row.createdEventsIds);
            friendsIds = JSON.parse(row.friendsIds);
            toSend = row;
            toSend.attendingEventsIds = attendingEventsIds;
            toSend.createdEventsIds = createdEventsIds;
            toSend.friendsIds = friendsIds;
        }
      }
      else {
        toSend = "1";
      }

      res.send(toSend);
    });
};