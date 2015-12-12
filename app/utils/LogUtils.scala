package utils

import java.io.{PrintWriter, StringWriter}

object LogUtils {

  def getStackTraceAsString(throwable: Throwable): String = {
    val stringWriter: StringWriter = new StringWriter()
    throwable.printStackTrace(new PrintWriter(stringWriter))
    stringWriter.toString
  }

}
