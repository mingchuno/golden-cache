package dao

import com.mongodb.DBObject
import com.mongodb.casbah.MongoCollection
import org.json4s.Extraction
import org.json4s.mongo.JObjectParser

trait MongoObjectHelper[A]{
  protected implicit val formats: org.json4s.Formats

  def dbObj2Obj[T](o: MongoCollection#T)(implicit m: Manifest[A]): A = {
    val json = JObjectParser.serialize(o)
    Extraction.extract[A](json)
  }

  def obj2DbObj(obj: A): DBObject = {
    JObjectParser.parse(Extraction.decompose(obj))
  }

}