package usermanager.domain.aggregates.user.write

import usermanager.domain.types._

class UserWrite(
  id: Id[UserWrite],
  userName: Name[UserWrite],
  email: Email[UserWrite],
  status: Status,
  versionNo: VersionNo[UserWrite],
  password: Password[UserWrite]
)
