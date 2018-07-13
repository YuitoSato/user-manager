package usermanager.domain.types

case class HashedPassword[+T](value: String) extends AnyVal

object HashedPassword {

  // TODO: yuito 正規表現を追加する
  val length = 60

  implicit def to[T](a: String): HashedPassword[T] = HashedPassword(a)
  implicit def from(b: HashedPassword[_]): String = b.value

}
