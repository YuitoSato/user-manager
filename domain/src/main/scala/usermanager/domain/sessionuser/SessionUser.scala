package usermanager.domain.sessionuser

import usermanager.domain.base.Entity
import usermanager.domain.types.{ Id, VersionNo }
import usermanager.domain.user.AbstractUser

case class SessionUser(
  id: Id[SessionUser],
  versionNo: VersionNo[SessionUser]
) extends Entity[SessionUser] {

  def userId: Id[AbstractUser] = id.asInstanceOf[Id[AbstractUser]]

}
