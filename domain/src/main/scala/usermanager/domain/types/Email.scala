package usermanager.domain.types

case class Email[+T](value: String) extends AnyVal {

  require(isValid)

  def isValid: Boolean = value.matches(Email.pattern) && value.length <= Email.maxLength

}
object Email {

  val pattern = """^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"""
  val maxLength = 150

  implicit def to[T](a: String): Email[T] = Email(a)
  implicit def from(b: Email[_]): String = b.value
//  implicit def iso[T]: Iso[String, Email[T]] = Iso(to, from)
}
