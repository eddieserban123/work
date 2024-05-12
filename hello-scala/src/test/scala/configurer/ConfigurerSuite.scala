package configurer

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable.{Map => MutableMap}

class ConfigurerSuite extends AnyFunSuite with Matchers{

  test("reading a map using general method and retrieve a value"){
    val configurer = Configurer

   val body = configurer.getValueFromConfigurationWithPath[MutableMap[String,String]]("endpoints.ims.body")
    body.get.get("content").get should not include "${"

  }


  test("reading a map using a specific method and retrieve a value"){
    val configurer = Configurer

    val body = configurer.getValuesAsMapFromConfigurationWithPath("endpoints.ims.body")
    body.get("content").get should not include   ("${file:details/ims/body.json}")

  }


  test("reading a simple value using a general method"){
    val configurer = Configurer
    val verb = configurer.getValueFinalFromConfigurationWithPath[String]("endpoints.ims.verb")

    verb.get shouldBe("POST")

  }


  test("reading a header map using a general method"){
    val configurer = Configurer
    val headers = configurer.getValuesAsMapFromConfigurationWithPath("endpoints.ims.headers")

    headers.size shouldBe 1

  }

  test("reading all params as a map. Most likely this one will be use"){
    val configurer = Configurer
    val params = configurer.getValuesAsMapFromConfigurationWithPath("endpoints.ims")

    params.size shouldBe 6

  }
}
