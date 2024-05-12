import client.{HttpClientImpl, HttpParamBuilder}
import configurer.Configurer

object HttpClientExample {
  def main(args: Array[String]): Unit = {
    val httpClient: HttpClientImpl = new HttpClientImpl

    val url = Configurer.getValueFinalFromConfigurationWithPath[String]("endpoints.ims.host").get
    val verb = Configurer.getValueFinalFromConfigurationWithPath[String]("endpoints.ims.verb").get
    val port = Configurer.getValueFinalFromConfigurationWithPath[Int]("endpoints.ims.port").get
    val headers = Configurer.getValuesAsMapFromConfigurationWithPath("endpoints.ims.headers").toMap
    val queryParams = Configurer.getValuesAsMapFromConfigurationWithPath("endpoints.ims.queryParams").toMap
    val body = Configurer.getValuesAsMapFromConfigurationWithPath("endpoints.ims.body").get("content").get

    val params = HttpParamBuilder()
      .withUrl(url)
      .withPort(port)
      .withVerb(verb)
      .withHeaders(headers)
      .withBody(body)
      .withQueryParams(queryParams)
      .build()

    httpClient.execute(params)


  }
}