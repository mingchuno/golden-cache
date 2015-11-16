package base

import org.scalamock.scalatest.MockFactory
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

trait HKGCacheSpecBase
  extends FlatSpecLike
  with BeforeAndAfter
  with GivenWhenThen
  with Matchers
  with MockFactory
  with BeforeAndAfterAll
  with ScalaFutures
  with OptionValues