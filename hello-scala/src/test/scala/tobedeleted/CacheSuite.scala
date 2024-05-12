package tobedeleted

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class CacheSuite extends AnyFunSuite with Matchers {

  test("First Cache test") {
    val cacheConfig = new CacheConfig()
    val cache: Cache[Int, String] = new CacheImpl(cacheConfig)
    cache.putValue(1, "Titi")
    cache getValue 1 shouldBe ("Titi")
  }

}
