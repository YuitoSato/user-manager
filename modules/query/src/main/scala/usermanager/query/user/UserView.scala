package usermanager.query.user

import usermanager.query.types.enums.Status
import usermanager.query.types.{ Email, Id, Name, VersionNo }

case class UserView(
  id: Id[UserView],
  userName: Name[UserView],
  email: Email[UserView],
  status: Status,
  versionNo: VersionNo[UserView],
)

object UserView {

  val TYPE: String = "UserView"

}
