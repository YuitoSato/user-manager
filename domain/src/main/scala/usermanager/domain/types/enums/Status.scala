package usermanager.domain.types.enums

import usermanager.domain.types.base.Isomorphism

sealed abstract class Status(val value: String) extends Enum[String]

object Status extends EnumCompanion[String, Status] with Isomorphism[String, Status]{

  val values = Seq(Status.Enable, Status.Disable)

  case object Enable extends Status("ENA")
  case object Disable extends Status("DIS")

  implicit def to[T](a: String): Status = Status.valueOf(a)
  implicit def from(b: Status): String = b.value

}
