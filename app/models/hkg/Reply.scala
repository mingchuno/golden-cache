package models.hkg

import org.joda.time.DateTime
import utils.HKGDataConverter

case class Reply(replyId: Int, 
                 authorName: String, 
                 authorGender: String, 
                 authorId: Int, 
                 messageDate: Option[DateTime], 
                 messageBody: String, 
                 isBlock: Boolean)

object Reply extends HKGDataConverter {
  def parseHKGFormat(
      replyId: Int, 
      authorName: String, 
      authorGender: String, 
      authorId: Int, 
      messageDate: String, 
      messageBody: String, 
      isBlock: String
    ): Reply = {
    new Reply(
      replyId = replyId,
      authorName = authorName,
      authorGender = authorGender,
      authorId = authorId,
      messageDate = convertHKGDateToDateTime(messageDate),
      messageBody = messageBody,
      isBlock = convertHKGBoolean(isBlock)
      )
  }
}
