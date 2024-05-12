package tobedeleted

trait Cache[T, U] {

  def getValue(key: T): U

  def getValues(iterable: Iterable[T]): Map[T, U]

  def putValue(key: T, value: U): Unit


}
