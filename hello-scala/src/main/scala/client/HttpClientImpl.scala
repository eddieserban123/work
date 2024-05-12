package client

import io.circe.parser.parse
import org.apache.http.client.methods.{HttpGet, HttpPost, HttpRequestBase}
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients

import java.io.IOException
import scala.io.Source

class HttpClientImpl {
  val httpClient = HttpClients.createDefault()

  def execute(httpParam: HttpParam): Unit = {

    val uriBuilder = new URIBuilder(httpParam.url)

    httpParam.queryParams.foreach { case (k, v) =>
      uriBuilder.addParameter(k, v)
    }

    val httpRequest = httpParam.verb match {
      case GET => new HttpGet(uriBuilder.build())
      case POST =>
        val post = new HttpPost(uriBuilder.build())
        post.setEntity(new StringEntity(httpParam.body))
        post
      case _ => new HttpGet("") // This could be problematic. Consider a more suitable default action.
    }

    httpParam.headers.foreach { case (k, v) =>
      httpRequest.addHeader(k, v)
    }

    //    val bearerToken = "YOUR_BEARER_TOKEN"
    //    httpRequest.addHeader("Authorization", s"Bearer $bearerToken")

    try {
      val response = httpClient.execute(httpRequest)
      val entity = response.getEntity

      if (entity != null) {
        val inputStream = entity.getContent
        val responseBody = Source.fromInputStream(inputStream).mkString
        println("Response body:")
        println(responseBody)

        // Parse the JSON response using Circe
        val json = parse(responseBody)
        json match {
          case Left(error) => println(s"Failed to parse JSON: $error")
          case Right(parsedJson) => println(s"Parsed JSON: $parsedJson")
            val userId = parsedJson.hcursor.downField("userId").as[Int]
            val id = parsedJson.hcursor.downField("id").as[Int]
            val title = parsedJson.hcursor.downField("title").as[String]
            val completed = parsedJson.hcursor.downField("completed").as[Boolean]
            println(s"userId: $userId")
            println(s"id: $id")
            println(s"title: $title")
            println(s"completed: $completed")
        }
      }
    } catch {
      case e: IOException => println("Error occurred: " + e.getMessage)
    } finally {
      httpClient.close()
    }
  }

}
