package configurer

import org.yaml.snakeyaml.Yaml

import scala.collection.JavaConverters._

object YamlReader {
  def readYaml(resourcePath: String): Option[Map[String, Any]] = {
    val inputStream = getClass.getResourceAsStream(resourcePath)
    Option(inputStream).map { stream =>
      val yaml = new Yaml()
      val yamlObject = yaml.load(stream).asInstanceOf[java.util.Map[String, Any]]
      parseYaml(yamlObject)
    }
  }

  private def parseYaml(yamlObject: java.util.Map[String, Any]): Map[String, Any] = {
    yamlObject.asScala.map {
      case (key, value: java.util.Map[String, Any]) =>
        key -> parseYaml(value)
      case (key, value) =>
        key -> value
    }.toMap
  }

  def main(args: Array[String]): Unit = {
    val resourcePath = "configuration.yaml"
    val yamlMap = readYaml(resourcePath)
    println(yamlMap)
  }
}
