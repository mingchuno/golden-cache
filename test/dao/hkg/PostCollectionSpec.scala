package dao.hkg

import base.HKGCacheSpecBase
import models.hkg._
import org.joda.time.DateTime

class PostCollectionSpec extends HKGCacheSpecBase with PostCollection {
  "a normal post obj" should "parse and un-parse to same object" in {
    val expected = Post(6081591,"人越大 就越難得到個種簡單平淡既開心",Some(new DateTime(1444479657000L)),Some(new DateTime(1444498814277L)),22,6,0,6,1,2,List(Reply(229018821,"知錯難改","M",361152,Some(new DateTime(1444479910797L)),"""<img src="/faces/frown.gif" alt=":-(" border="0" />""", isBlock = false)))
    dbObj2PostObj(postObj2DbObj(expected)) shouldBe expected
  }
}