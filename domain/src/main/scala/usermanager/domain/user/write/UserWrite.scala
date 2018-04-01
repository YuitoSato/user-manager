package usermanager.domain.user.write

import usermanager.domain.types._
import usermanager.domain.user.AbstractUser
import usermanager.domain.user.read.UserRead

class UserWrite(
  val id: Id[UserRead],
  val email: Email[UserRead],
  val status: Status,
  val versionNo: VersionNo[UserRead],
  password: Password[UserRead]
) extends AbstractUser
