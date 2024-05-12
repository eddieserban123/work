package tobedeleted
trait Storage[T] {

  def put(t:T):Unit

  def get():T

}
