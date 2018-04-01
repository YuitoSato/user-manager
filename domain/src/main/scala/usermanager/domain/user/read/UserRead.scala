package usermanager.domain.user.read

import usermanager.domain.types.{ Email, Id, Status, VersionNo }
import usermanager.domain.user.AbstractUser

case class UserRead(
  id: Id[UserRead],
  email: Email[UserRead],
  status: Status,
  versionNo: VersionNo[UserRead]
) extends AbstractUser
