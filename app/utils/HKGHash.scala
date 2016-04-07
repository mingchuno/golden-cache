package utils

import play.api.Logger

object HKGHash {
  def md5(s: String) = {
    val m = java.security.MessageDigest.getInstance("MD5")
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(1, m.digest()).toString(16)
  }

  def getHash(forum:String, pageNum:Int, filterMode:String, sensorMode:String) = {
    val m:String = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date) + "_HKGOLDEN_" + "%GUEST%" + "_$API#Android_1_2^" +
      forum + "_" + pageNum + "_" + filterMode + "_" + sensorMode
    var md5Str:String = md5(m)
    md5Str
  }
}