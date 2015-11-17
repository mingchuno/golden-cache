package dao

import com.mongodb.casbah.Imports._
import play.api.Play
import com.typesafe.config.ConfigFactory

object MongoDAO {
  private val config = ConfigFactory.load
  config.checkValid(ConfigFactory.defaultReference)

  private val HOST: String = config.getString("golden.mongo.host")
  private val PORT: Int = config.getInt("golden.mongo.port")
  
  val uri = MongoClientURI(s"mongodb://$HOST:$PORT/")
  val mongoClient =  MongoClient(uri)
  val db = mongoClient("hkg")
}