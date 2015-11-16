package dao

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import play.api.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait MongoHelper[T] extends MongoObjectHelper[T]{

  protected implicit val formats: org.json4s.Formats

  def collection(name: String) = MongoDAO.db(name)

  def findOneAnd2Obj(name: String)(o: MongoDBObject, fields: Option[MongoDBObject] = None)(implicit m: Manifest[T]): Option[T] = {
    Logger.info(s"Query is: ${o.toString()}")
    val dbOpt = fields match {
      case Some(f) =>
        collection(name).findOne(o = o, fields = f)
      case None =>
        collection(name).findOne(o = o)
    }
    Logger.info(dbOpt.toString)
    dbOpt.map(dbObj2Obj)
  }

  def upsertObjToDBBlocking(name: String)(query: MongoDBObject, obj: T) = {
    collection(name).update(query, obj2DbObj(obj), upsert = true)
  }

  def upsertObjToDBAsync(name: String)(query: MongoDBObject, obj: T) = Future(
    upsertObjToDBBlocking(name)(query = query , obj = obj)
  )

}