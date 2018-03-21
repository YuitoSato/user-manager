package usermanager.domain.user

import usermanager.domain.types.{ Email, Id, Status, VersionNo }

case class User(
  userId: Id[User],
  email: Email[User],
  userStatus: Status,
  versionNo: VersionNo[User]
)
