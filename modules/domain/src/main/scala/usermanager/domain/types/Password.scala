package usermanager.domain.types


case class Password[+T](value: String) extends AnyVal {

  def isValid: Boolean = value.length >= Password.minLength && value.length <= Password.maxLength

}

object Password {

  // TODO: yuito 正規表現を追加する
  val maxLength = 60
  val minLength = 8

  implicit def to[T](a: String): Password[T] = Password(a)
  implicit def from(b: Password[_]): String = b.value

}
