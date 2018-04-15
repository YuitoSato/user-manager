package usermanager.domain.aggregates.user.write

import usermanager.domain.types._

object MockUserWrite {

  def apply(
    id: Id[UserWrite] = "1",
    userName: Name[UserWrite] = "Hoge",
    email: Email[UserWrite] = "hoge@example.com",
    password: Password[UserWrite] = "password",
    status: Status = "ENA",
    versionNo: VersionNo[UserWrite] = 0
  ): UserWrite = UserWrite(
    id = id,
    userName = userName,
    email = email,
    password = password,
    status = status,
    versionNo = versionNo
  )

}
