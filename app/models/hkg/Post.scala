package models.hkg

import org.joda.time.DateTime
import utils.HKGDataConverter

/**
 * Message_ID & Current_Pages is the key!
 */
case class Post(messageId:Int, 
                messageTitle: String,
                messageDate: Option[DateTime],
                lastReplyDate: Option[DateTime], 
                totalReplies: Int, 
                ratingGood:Int, 
                ratingBad:Int, 
                rating: Int, 
                totalPages: Int, 
                currentPages: Int,
                messages: List[Reply]) {
  def toHistoryItem = {
    HistoryItem(id = messageId, title = messageTitle)
  }
}

object Post extends HKGDataConverter {
  // custom apply function
  def parseHKGFormat(
      messageId:Int, 
      messageTitle: String, 
      messageDate: String,
      lastReplyDate: String, 
      totalReplies: Int, 
      ratingGood:Int, 
      ratingBad:Int, 
      rating: Int, 
      totalPages: Int, 
      currentPages: Int,
      messages: List[Reply]
      ): Post = {
    
    new Post(
      messageId = messageId, 
      messageTitle = messageTitle, 
      messageDate = convertHKGDateToDateTime(messageDate),
      lastReplyDate = convertHKGDateToDateTime(lastReplyDate), 
      totalReplies = totalReplies, 
      ratingGood = ratingGood, 
      ratingBad = ratingBad, 
      rating = rating, 
      totalPages = totalPages, 
      currentPages = currentPages,
      messages = messages)
  }
}
