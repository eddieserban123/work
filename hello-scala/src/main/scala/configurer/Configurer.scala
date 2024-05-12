package configurer

import io.circe.parser._
import io.circe.{Json, JsonObject}
import org.yaml.snakeyaml.Yaml

import scala.collection.mutable.{Map => MutableMap}
import scala.io.Source
import scala.jdk.CollectionConverters.MapHasAsScala
import scala.util.matching.Regex

object Configurer {

  var parameters: MutableMap[String, Any] = MutableMap.empty
  var configuration: MutableMap[String, Any] = MutableMap.empty

  private var initialized = false

  def init(): Unit = {
    if (!initialized) {
      readGeneralParams()
      readGeneralConfiguration()
      initialized = true
    }
  }


  def readYaml(resourcePath: String): MutableMap[String, Any] = {
    val inputStream = getClass.getResourceAsStream(resourcePath)
    val yaml = new Yaml()
    val yamlObject = yaml.load(inputStream).asInstanceOf[java.util.Map[String, Any]]
    parseYaml(yamlObject)

  }

  private def parseYaml(yamlObject: java.util.Map[String, Any]): MutableMap[String, Any] = {
    val resultMap = MutableMap[String, Any]()
    yamlObject.asScala.foreach {
      case (key, value: java.util.Map[String, Any]) =>
        resultMap += (key -> parseYaml(value))
      case (key, value) =>
        resultMap += (key -> value)
    }
    resultMap
  }

  init()

  def readGeneralParams(): Unit = {
    parameters = readYaml("/params.yaml")
  }

  def readGeneralConfiguration(): Unit = {
    configuration = readYaml("/configuration.yaml")

    traverseMap(configuration)
    println(configuration)

  }

  def getValueFromParameters[T](key: String)(implicit tag: reflect.ClassTag[T]): Option[T] = {
    parameters.get(key) match {
      case Some(value: T) => Some(value)
      case _ => None
    }
  }

  def getValueFromConfiguration[T](key: String)(implicit tag: reflect.ClassTag[T]): Option[T] = {
    configuration.get(key) match {
      case Some(value: T) => Some(value)
      case _ => None
    }
  }


  def getValuesAsMapFromConfigurationWithPath(key: String): MutableMap[String, String] = {
    getValueFromConfigurationWithPath[MutableMap[String, String]](key).getOrElse(MutableMap.empty)
  }

  def getValueFinalFromConfigurationWithPath[T](key: String): Option[T] = {
    val (first, last) = splitString(key)
    val result: Option[MutableMap[String, String]] = getValueFromConfigurationWithPath[MutableMap[String, String]](first)

    result match {
      case Some(map) =>
        map.get(last).asInstanceOf[Option[T]]
      case None =>
        None
    }
  }


  def getValueFromConfigurationWithPath[T](key: String)(implicit tag: reflect.ClassTag[T]): Option[T] = {
    val paths = key.split("\\.").filter(_.nonEmpty)
    var data = configuration
    //these are intermediates path of maps
    for (path <- paths) {
      data = data(path).asInstanceOf[MutableMap[String, Any]]
    }

    data match {
      case value: T => Some(value)
      case _ => None
    }
  }

  def traverseMap(map: MutableMap[String, Any]): Unit = {
    for ((key, value) <- map) {
      value match {
        case innerMap: MutableMap[String, Any] =>
          traverseMap(innerMap)
        case _ =>
          value match {
            case str: String => if (str.contains("${")) {
              map.update(key, getTemplateVariable(str))
            }
            case _ =>

          }
      }
    }
  }

  private def getTemplateVariable(value: String): String = {
    val pattern: Regex = "\\$\\{([^}]*)\\}".r

    val matches = pattern.findAllMatchIn(value).toList.map(_.group(1)).head
    if (matches.startsWith("file:")) {
      val path = matches.substring("file:".length)

      val jsonString = Source.fromResource(path).getLines().mkString("\n")
      val parsedJson = parse(jsonString)

      val replacedJson = parsedJson.map(replaceParameters(_, parameters))
      return replacedJson.getOrElse(Json.Null).toString
    } else {
      return pattern.replaceAllIn(value,parameters.get(matches).get.toString)
    }
    ""
  }


  // Recursively replace placeholders with actual values
  private def replaceValue(value: Json, parameters: MutableMap[String, Any]): Json = {
    value.fold(
      jsonNull = Json.Null,
      jsonBoolean = _ => value,
      jsonNumber = _ => value,
      jsonString = str => {
        val replacedStr = parameters
          .map {
            case (key, value: String) => key -> value
            case (key, value: Int) => key -> value.toString
            case (key, value: Boolean) => key -> value.toString
            case (key, other) => key -> other.toString
          }
          .foldLeft(str)((acc, entry) => acc.replace(s"{{${entry._1}}}", entry._2))
        Json.fromString(replacedStr)
      },
      jsonArray = arr => Json.arr(arr.map(replaceValue(_, parameters)): _*),
      jsonObject = obj => replaceParameters(obj.toJson, parameters)
    )
  }

  // Function to replace placeholders with actual values
  def replaceParameters(json: Json, parameters: MutableMap[String, Any]): Json = {
    json.mapObject { obj =>
      val replacedFields = obj.toMap.map {
        case (key, value) => key -> replaceValue(value, parameters)
      }
      JsonObject.fromMap(replacedFields)
    }
  }


  def splitString(str: String): (String, String) = {
    val lastIndex = str.lastIndexOf('.')
    if (lastIndex != -1) {
      val firstPart = str.substring(0, lastIndex)
      val secondPart = str.substring(lastIndex + 1)
      (firstPart, secondPart)
    } else {
      // Handle case where there is no dot in the string
      (str, "")
    }
  }


}
