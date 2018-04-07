package usermanager.domain.aggregates.user.read

import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.types._

case class UserRead(
  id: Id[UserRead],
  userName: Name[UserRead],
  email: Email[UserRead],
  password: HashedPassword[UserRead],
  status: Status,
  versionNo: VersionNo[UserRead],
) {

  def toSessionUser: SessionUser = {
    SessionUser(
      id = id.value,
      userName = userName.value,
      versionNo = 1
    )
  }

}
