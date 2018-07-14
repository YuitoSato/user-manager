package usermanager.query.types

case class Email[+T](value: String) extends AnyVal { self =>

//  require(Email.isValid(self))

}

object Email {

  def isValid[T](value: Email[T]): Boolean = value.matches(Email.pattern) && value.length <= Email.maxLength

  val pattern = """^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"""
  val maxLength = 150

  implicit def to[T](a: String): Email[T] = Email(a)
  implicit def from(b: Email[_]): String = b.value
//  implicit def iso[T]: Iso[String, Email[T]] = Iso(to, from)
}
