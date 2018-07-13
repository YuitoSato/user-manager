package usermanager.domain.types

import java.time.LocalDateTime

case class CreatedAt[+T](value: LocalDateTime) extends AnyVal


