package tobedeleted

import scala.reflect.runtime.universe._

class StorageImpl[T: TypeTag] extends Storage[T] {
  private var v: Option[T] = None

  override def put(input: T): Unit = {
    v = Some(input);
    typeOf[T] match {
      case t if t =:= typeOf[String] => println(s"Processing String: $input")
      case t if t =:= typeOf[Int] => println(s"Processing Int: $input")
      case _ => println("Unknown type !")
    }
  }

  override def get(): T = v.getOrElse(throw new NoSuchElementException("no value stored !"))
}
