package usermanager.domain.aggregates.user

import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.error.DomainError
import usermanager.domain.helpers.HashHelper
import usermanager.domain.types._

import scalaz.{ -\/, \/, \/- }

case class User(
  id: Id[User],
  userName: Name[User],
  email: Email[User],
  password: HashedPassword[User],
  status: Status,
  versionNo: VersionNo[User],
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
      \/-(())
    } else {
      -\/(DomainError.Unauthorized)
    }
  }

}

object User {

  val TYPE: String = "User"

}
