package usermanager.domain.transaction.delete

case class Deleted(value: Boolean) extends AnyVal

object Deleted {

  implicit def to[T](a: Boolean): Deleted = Deleted(a)
  implicit def from(b: Deleted): Boolean = b.value

}
