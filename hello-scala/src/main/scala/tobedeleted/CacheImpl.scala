package tobedeleted

import org.cache2k.Cache2kBuilder

import java.util.concurrent.TimeUnit
import scala.jdk.CollectionConverters._

class CacheImpl(cfg: CacheConfig) extends Cache[Int, String] {

  val cache = new Cache2kBuilder[Int, String]() {}.expireAfterWrite(1L, TimeUnit.MINUTES).build()

  override def getValue(key: Int): String = {
    cache.computeIfAbsent(key, () => {
      key match {
        case 2 => "Titi Cocos"
        case 4 => "Titi Smenaru"
        case other => s"$other"
      }
    })
  }

  override def putValue(key: Int, value: String) = {
    cache.put(key, value)
  }

  override def getValues(iterable: Iterable[Int]): Map[Int, String] = {
    cache.getAll(iterable.asJava).asScala.toMap
  }
}
