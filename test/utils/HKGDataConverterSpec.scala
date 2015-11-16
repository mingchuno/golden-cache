package utils

import base.HKGCacheSpecBase
import org.joda.time.DateTime

class HKGDataConverterSpec extends HKGCacheSpecBase with HKGDataConverter {
  "a 0 timestamp" should "return new DateTime(0L)" in {
    val s = """\/Date(0)\/"""
    val expected = new DateTime(0L)
    convertHKGDateToDateTime(s).value shouldBe expected
    info(expected.toString)
  }

  "a normal timestamp" should "return correct DateTime" in {
    val s = """\/Date(1444493112560)\/"""
    val expected = new DateTime(1444493112560L)
    convertHKGDateToDateTime(s).value shouldBe expected
    info(expected.toString)
  }

  "some shit" should "return None" in {
    convertHKGDateToDateTime("""\/Date()\/""") shouldBe None
    convertHKGDateToDateTime("""asdasfsadfas""") shouldBe None
  }
}