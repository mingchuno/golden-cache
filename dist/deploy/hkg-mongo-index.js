console.log("----- Start building index -----");
db.hkg.post.createIndex({"messageId" : 1, "currentPages" : 1}, {"background":true,"unique": true, "sparse": true});
console.log("----- Finish building index -----");