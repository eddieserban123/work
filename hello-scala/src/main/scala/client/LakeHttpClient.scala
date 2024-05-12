package client



trait LakeHttpClient[T] {

  def execute():T
}
