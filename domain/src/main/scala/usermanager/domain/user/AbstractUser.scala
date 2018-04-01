package usermanager.domain.user

import usermanager.domain.base.Entity
import usermanager.domain.types._

trait AbstractUser extends Entity[AbstractUser]{

  val id: Id[AbstractUser]

  val email: Email[AbstractUser]

  val status: Status

  val versionNo: VersionNo[AbstractUser]

}
