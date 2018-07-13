package usermanager.domain.aggregates.user

import usermanager.domain.types._
import usermanager.domain.types.enums.Status

object MockUser {

  def apply(
    id: Id[User] = "1",
    userName: Name[User] = "Hoge",
    email: Email[User] = "hoge@example.com",
    password: HashedPassword[User] = "password",
    status: Status = "ENA",
    versionNo: VersionNo[User] = 0
  ): User = User(
    id = id,
    userName = userName,
    email = email,
    password = password,
    status = status,
    versionNo = versionNo
  )

}
