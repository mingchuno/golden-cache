package service

import base.HKGCacheSpecBase
import java.util.UUID

class UserHistoryServiceSpec extends HKGCacheSpecBase with UserHistoryService {
  "UserHistoryService basic function" should "work as expected" in {
    val uuid = UUID.randomUUID().toString
    getHistory(uuid) should have size 0

    val fakeHistory = HistoryItem(id = 1, title = "does-not-matter")

    saveHistory(uuid, fakeHistory) shouldBe true

    val result = getHistory(uuid)

    result should have size 1
    result.head shouldBe fakeHistory

    // get again
    getHistory(uuid) should have size 1

  }
}
