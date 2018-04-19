package usermanager.domain.aggregates.session

import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.types.{ Id, Name, VersionNo }

object MockSessionUser {

  def apply(
    id: Id[SessionUser] = "1",
    userName: Name[SessionUser] = "hoge",
    versionNo: VersionNo[SessionUser] = 0
  ): SessionUser = {
    SessionUser(
      id = id,
      userName = userName,
      versionNo = versionNo
    )
  }

}
