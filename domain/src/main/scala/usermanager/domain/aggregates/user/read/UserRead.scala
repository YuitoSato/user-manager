package usermanager.domain.aggregates.user.read

import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.error.DomainError
import usermanager.domain.helpers.HashHelper
import usermanager.domain.types._

import scalaz.{ -\/, \/, \/- }

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

  def authenticate(plainPassword: String)(implicit hashHelper: HashHelper): DomainError \/ Unit = {
    if (hashHelper.checkPassword(plainPassword, password)) {
      -\/(DomainError.Unauthorized)
    } else {
      \/-(())
    }
  }

}
