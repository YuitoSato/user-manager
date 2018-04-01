package usermanager.domain.session

import usermanager.domain.base.Entity
import usermanager.domain.types.{ Id, VersionNo }
import usermanager.domain.user.AbstractUser

case class Session(
  id: Id[Session],
  versionNo: VersionNo[Session]
) extends Entity[Session] {

  def userId: Id[AbstractUser] = id.asInstanceOf[Id[AbstractUser]]

}
