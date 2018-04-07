package usermanager.domain.aggregates.sessionuser

import usermanager.domain.aggregates.Entity
import usermanager.domain.types.{ Id, Name, VersionNo }
import usermanager.domain.aggregates.user.read.UserRead

case class SessionUser(
  id: Id[SessionUser],
  userName: Name[SessionUser],
  versionNo: VersionNo[SessionUser]
) extends Entity[SessionUser] {

  def userId: Id[UserRead] = id.asInstanceOf[Id[UserRead]]

}
