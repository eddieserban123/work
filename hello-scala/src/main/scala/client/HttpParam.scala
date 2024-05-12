package client


sealed trait HttpVerb

case object GET extends HttpVerb

case object POST extends HttpVerb

case object PUT extends HttpVerb

object HttpVerb {
  def fromString(value: String): HttpVerb = value.toUpperCase match {
    case "GET" => GET
    case "POST" => POST
    case "PUT" => PUT
    case _ => throw new IllegalArgumentException(s"Unexpected type: ${value}")
  }
}

case class HttpParam(
                      url: String = "",
                      port: Int = 80,
                      verb: HttpVerb = GET,
                      headers: Map[String, String] = Map.empty,
                      queryParams: Map[String, String] = Map.empty,
                      body: String = ""
                    )

object HttpParam {
  def builder(): HttpParamBuilder = HttpParamBuilder()
}



