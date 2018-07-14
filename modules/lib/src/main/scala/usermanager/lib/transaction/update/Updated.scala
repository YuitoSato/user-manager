package usermanager.lib.error.transaction.update

case class Updated(value: Boolean) extends AnyVal

object Updated {

  implicit def to[T](a: Boolean): Updated = Updated(a)
  implicit def from(b: Updated): Boolean = b.value

}
