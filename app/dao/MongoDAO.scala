package dao

import com.mongodb.casbah.Imports._

object MongoDAO {
  val uri = MongoClientURI("mongodb://localhost:27017/")
  val mongoClient =  MongoClient(uri)
  val db = mongoClient("hkg")
}