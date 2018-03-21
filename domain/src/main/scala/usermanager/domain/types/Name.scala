package usermanager.domain.types

case class Name[T](value: String) extends AnyVal {

  def isValid: Boolean = value.length <= Name.maxLength
}

object Name {

  val maxLength = 20

  implicit def to[T](a: String): Name[T] = Name(a)
  implicit def from(b: Name[_]): String = b.value
//  implicit def iso[T]: Iso[String, Name[T]] = Iso(to, from)
}
