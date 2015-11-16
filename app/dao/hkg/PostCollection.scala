package dao.hkg

import com.mongodb.casbah.commons.MongoDBObject
import dao.MongoHelper
import models.hkg.Post
import org.json4s.NoTypeHints
import org.json4s.ext.JodaTimeSerializers
import org.json4s.native.Serialization

trait PostCollection {

  private val helper: MongoHelper[Post] = new MongoHelper[Post]{
    protected implicit val formats = Serialization.formats(NoTypeHints) ++ JodaTimeSerializers.all
  }

  val postCollection = helper.collection("hkg.post")

  def upsertPost(post: Post) = {
    postCollection.update(
      q = MongoDBObject("messageId" -> post.messageId, "currentPages" -> post.currentPages),
      o = postObj2DbObj(post),
      upsert = true
    )
  }

  def getPostByIdAndPage(id: Int, page: Int): Option[Post] = {
    val q = MongoDBObject("messageId" -> id, "currentPages" -> page)
    postCollection.findOne(q).map(dbObj2PostObj)
  }

  def getPostMaxPage(id: Int): Option[Int] = {
    val result = postCollection
      .find(MongoDBObject("messageId" -> id))
      .sort(MongoDBObject("currentPages" -> -1))
      .limit(1)
      .map(dbObj2PostObj)
      .toList
    if (result.isEmpty) {
      None
    } else {
      Some(result.head.currentPages)
    }
  }

  def postObj2DbObj(p: Post) = helper.obj2DbObj(p)
  def dbObj2PostObj(o: com.mongodb.DBObject): Post = helper.dbObj2Obj(o)

}