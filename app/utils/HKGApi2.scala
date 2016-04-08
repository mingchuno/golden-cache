package utils

import play.api.Logger

object HKGApi2 {
  def md5(s: String) = {
    java.security.MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02x".format(_)).mkString
  }

  def getHash(forum:String, pageNum:Int, filterMode:String, sensorMode:String) = {
    val m:String = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date) + "_HKGOLDEN_" + "%GUEST%" + "_$API#Android_1_2^" +
      forum + "_" + pageNum + "_" + filterMode + "_" + sensorMode
    var md5Str:String = md5(m)
    md5Str
  }

  def getTopicHash(messageId:Int, startNum:Int, userId:Int, filterMode:String, sensorMode:String) = {
    val m: String = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date) + "_HKGOLDEN_" + "%GUEST%" + "_$API#Android_1_2^" +
      messageId + "_" + startNum + "_" + filterMode + "_" + sensorMode
    var md5Str: String = md5(m)
    md5Str
  }

}