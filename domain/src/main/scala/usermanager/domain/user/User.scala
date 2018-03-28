package usermanager.domain.user

import java.time.LocalDateTime

import usermanager.domain.types.{ Email, Id, Status, VersionNo }

case class User(
  userId: Id[User],
  email: Email[User],
  status: Status,
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime,
  versionNo: VersionNo[User]
)
