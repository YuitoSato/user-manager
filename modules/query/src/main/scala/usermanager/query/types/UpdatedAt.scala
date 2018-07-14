package usermanager.query.types

import java.time.LocalDateTime

case class UpdatedAt[+T](value: LocalDateTime)
