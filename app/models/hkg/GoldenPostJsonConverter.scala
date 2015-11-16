package models.hkg

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{Json, _}

trait GoldenPostJsonConverter {

  implicit val replyReads: Reads[Reply] = (
    (__ \ "Reply_ID").read[Int] and
    (__ \ "Author_Name").read[String] and
    (__ \ "Author_Gender").read[String] and
    (__ \ "Author_ID").read[Int] and
    (__ \ "Message_Date").read[String] and
    (__ \ "Message_Body").read[String] and
    (__ \ "isBlock").read[String]
  )(Reply.parseHKGFormat _)

  implicit val replyWrites = Json.writes[Reply]

//  implicit val replyFormat = Format(replyReads, replyWrites)

  implicit val postReads: Reads[Post] = (
    (__ \ "Message_ID").read[Int] and
    (__ \ "Message_Title").read[String] and
    (__ \ "Message_Date").read[String] and
    (__ \ "Last_Reply_Date").read[String] and
    (__ \ "Total_Replies").read[Int] and
    (__ \ "Rating_Good").read[Int] and
    (__ \ "Rating_Bad").read[Int] and
    (__ \ "Rating").read[Int] and
    (__ \ "Total_Pages").read[Int] and
    (__ \ "Current_Pages").read[Int] and
    (__ \ "messages").read[List[Reply]]
  )(Post.parseHKGFormat _)

  implicit val postWrites = Json.writes[Post]

//  implicit val postFormat = Format(postReads, postWrites)
  
  implicit val topicReads: Reads[Topic] = (
    (__ \ "Message_ID").read[Int] and
    (__ \ "Message_Title").read[String] and
    (__ \ "Author_ID").read[Int] and
    (__ \ "Author_Name").read[String] and
    (__ \ "Last_Reply_Date").read[String] and
    (__ \ "Total_Replies").read[Int] and
    (__ \ "Rating").read[Int]
  )(Topic.parseHKGFormat _)

  implicit val topicWrites = Json.writes[Topic]

  implicit val topicsReads: Reads[Topics] = 
    (__ \ "topic_list").read[List[Topic]].map(Topics(_))

  implicit val topicsWrites = Json.writes[Topics]
}