package utils

import org.joda.time.DateTime
import scala.util.Try

trait HKGDataConverter {

  protected val datePattern = """\S+Date\((\d+)\)\S+""".r

  protected def convertHKGDateToDateTime(s: String): Option[DateTime] = {
    // very magic number here since the return format from hkg is: "\/Date(1444493112560)\/", wtf!
    s match {
      case datePattern(timestamp) =>
        Try(new DateTime(timestamp.toLong)).toOption
      case _ =>
        None
    }
  }

  protected def convertHKGBoolean(s: String): Boolean = {
    s == "Y"
  }
}