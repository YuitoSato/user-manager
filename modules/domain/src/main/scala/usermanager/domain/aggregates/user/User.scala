package usermanager.domain.aggregates.user

import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.helpers.HashHelper
import usermanager.domain.types._
import scalaz.{ -\/, \/, \/- }
import usermanager.domain.types.enums.Status
import usermanager.lib.error
import usermanager.lib.error.Error

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

  def authenticate(plainPassword: String)(implicit hashHelper: HashHelper): error.Error \/ Unit = {
    if (hashHelper.checkPassword(plainPassword, password)) {
      \/-(())
    } else {
      -\/(Error.Unauthorized)
    }
  }

}

object User {

  val TYPE: String = "User"

}
