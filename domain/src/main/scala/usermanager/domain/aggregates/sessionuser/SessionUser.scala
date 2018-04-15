package usermanager.domain.aggregates.sessionuser

import usermanager.domain.aggregates.Entity
import usermanager.domain.aggregates.user.User
import usermanager.domain.types.{ Id, Name, VersionNo }

case class SessionUser(
  id: Id[SessionUser],
  userName: Name[SessionUser],
  versionNo: VersionNo[SessionUser]
) extends Entity[SessionUser] {

  def userId: Id[User] = id.asInstanceOf[Id[User]]

}

object SessionUser {

  val TYPE = "SessionUser"

}
