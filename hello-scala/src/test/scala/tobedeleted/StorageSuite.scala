package tobedeleted

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class StorageSuite extends AnyFunSuite with Matchers {

  test("Simple storage suite") {
    val storage = new StorageImpl[String]
    storage.put("Titi")
    storage.get() shouldBe "Titi"
  }
}
