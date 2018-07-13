package usermanager.domain.types

import java.time.LocalDateTime

case class UpdatedAt[+T](value: LocalDateTime)
