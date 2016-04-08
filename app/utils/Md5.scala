package utils

object Md5 {
  def hash(s: String) = {
    java.security.MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02x".format(_)).mkString
  }
}