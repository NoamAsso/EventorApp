const sqlite3 = require('sqlite3').verbose();

let db = new sqlite3.Database('./db/eventorDB.db',(err) => {
    if (err) {
        return console.error(err.message);
    }
    console.log('Connected to the events.db SQlite database.');
});
db.serialize(() => {
    db.run('CREATE TABLE events(id INTEGER PRIMARY KEY, adminUserId int, intCreationDate int, ' +
           'intDate int, creationDate text, date text, ' +
           'maxUsers int, currentUsers int, category text, description text, ' +
           'price int, friendsById text, isPrivate int, eventImage blob, ' +
           'placeID text, latitude real, longitude real)')

      .run('CREATE TABLE users(userId INTEGER PRIMARY KEY, userName text, password text, ' +
           'age int, mail text, phoneNum text, sex text, attendingEventsIds text, ' +
           'createdEventsIds text, friendsIds text, CONSTRAINT username_unique UNIQUE (userName))');
});

db.close();