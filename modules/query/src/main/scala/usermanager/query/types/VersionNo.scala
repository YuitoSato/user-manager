package usermanager.query.types

case class VersionNo[+T](value: Int) extends AnyVal

object VersionNo {

  implicit def to[T](a: Int): VersionNo[T] = VersionNo(a)
  implicit def from(b: VersionNo[_]): Int = b.value
  
}
