package models.hkg

import org.joda.time.DateTime
import utils.HKGDataConverter

case class Topic(messageId:Int, 
                 messageTitle: String, 
                 authorId: Int,
                 authorName: String,
                 lastReplyDate: Option[DateTime], 
                 totalReplies: Int, 
                 rating: Int)

object Topic extends HKGDataConverter {
  // custom apply function
  def parseHKGFormat(
      messageId:Int, 
      messageTitle: String, 
      authorId: Int,
      authorName: String,
      lastReplyDate: String, 
      totalReplies: Int, 
      rating: Int
      ): Topic = {
    
    new Topic(
      messageId = messageId, 
      messageTitle = messageTitle, 
      authorId = authorId,
      authorName = authorName,
      lastReplyDate = convertHKGDateToDateTime(lastReplyDate), 
      totalReplies = totalReplies, 
      rating = rating
    )
  }
}
