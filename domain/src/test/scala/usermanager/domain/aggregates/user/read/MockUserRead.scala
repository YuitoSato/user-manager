package usermanager.domain.aggregates.user.read

import usermanager.domain.types._

object MockUserRead {

  def apply(
    id: Id[UserRead] = "1",
    userName: Name[UserRead] = "Hoge",
    email: Email[UserRead] = "hoge@example.com",
    password: HashedPassword[UserRead] = "password",
    status: Status = "ENA",
    versionNo: VersionNo[UserRead] = 0
  ): UserRead = UserRead(
    id = id,
    userName = userName,
    email = email,
    password = password,
    status = status,
    versionNo = versionNo
  )

}
