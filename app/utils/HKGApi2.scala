package utils

import play.api.Logger

object HKGApi2 {
  private val apiEndpoint = "http://android-1-2.hkgolden.com"

  def getTopicUrl (channel:String, pageNum:Int, filter:String, sensor:String):String = {
    val key = getTopicHash(channel, pageNum, filter, "N")
    s"$apiEndpoint/newTopics.aspx?s=$key&type=$channel&returntype=json&page=$pageNum&filtermode=$filter&sensormode=N&user_id=0&block=Y"
  }

  def getPostUrl ():String = {
    s"$apiEndpoint/newView.aspx"
  }

  def getTopicHash(forum:String, pageNum:Int, filterMode:String, sensorMode:String) = {
    val m:String = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date) + "_HKGOLDEN_" + "%GUEST%" + "_$API#Android_1_2^" +
      forum + "_" + pageNum + "_" + filterMode + "_" + sensorMode
    Md5.hash(m)
  }

  def getPostHash(messageId:Int, startNum:Int, userId:Int, filterMode:String, sensorMode:String) = {
    val m: String = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date) + "_HKGOLDEN_" + "%GUEST%" + "_$API#Android_1_2^" +
      messageId + "_" + startNum + "_" + filterMode + "_" + sensorMode
    Md5.hash(m)
  }

}
