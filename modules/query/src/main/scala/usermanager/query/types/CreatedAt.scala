package usermanager.query.types

import java.time.LocalDateTime

case class CreatedAt[+T](value: LocalDateTime) extends AnyVal


