package client

case class HttpParamBuilder private(
                                     url: String = "",
                                     port: Int = 80,
                                     verb: HttpVerb = GET,
                                     headers: Map[String, String] = Map.empty,
                                     queryParams:Map[String, String] = Map.empty,
                                     body: String = ""
                                   ) {

  def withUrl(url: String): HttpParamBuilder =
    copy(url = url)

  def withPort(port: Int): HttpParamBuilder =
    copy(port = port)

  def withVerb(verb: HttpVerb): HttpParamBuilder =
    copy(verb = verb)

  def withVerb(verb: String): HttpParamBuilder =
    withVerb(HttpVerb.fromString(verb))

  def withHeaders(headers: Map[String, String]): HttpParamBuilder =
    copy(headers = headers)

  def withQueryParams(queryParams: Map[String, String]): HttpParamBuilder =
    copy(queryParams = queryParams)

  def withBody(body: String): HttpParamBuilder =
    copy(body = body)

  def build(): HttpParam = HttpParam(
    url = url,
    port = port,
    verb = verb,
    headers = headers,
    queryParams = queryParams,
    body = body
  )
}

object HttpParamBuilder {
  def apply(): HttpParamBuilder = new HttpParamBuilder()
}
