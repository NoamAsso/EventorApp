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
        sex = req.body.sex;

    let placeholders = Array(7).join("?");
    placeholders = placeholders.split("").join(',');
    db.serialize(() => {
        let sql1 = 'SELECT userName FROM users WHERE userName = ?';
        let sql2 = 'INSERT INTO users(userName, password, age, mail, phoneNum, sex) ' +
                   'VALUES (' + placeholders + ')';

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
        .run(sql2, [userName, password, age, mail, phoneNum, sex], function(err) {
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

// 1. when registering, check if username already exists
// 2. find user by name. if not found return 1. if password wrong return 2. otherwise return user

exports.loginProcess = function (req, res, next) {
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
            toSend = row;
        }
      }
      else {
        toSend = "1";
      }
      res.send(toSend);
    });
};