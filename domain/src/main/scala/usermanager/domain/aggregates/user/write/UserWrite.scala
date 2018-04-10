package usermanager.domain.aggregates.user.write

import usermanager.domain.types._

case class UserWrite(
  id: Id[UserWrite],
  userName: Name[UserWrite],
  email: Email[UserWrite],
  password: Password[UserWrite],
  status: Status,
  versionNo: VersionNo[UserWrite],
)
