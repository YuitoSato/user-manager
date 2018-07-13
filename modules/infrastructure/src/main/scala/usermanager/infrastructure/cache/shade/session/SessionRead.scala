package usermanager.infrastructure.cache.shade.session

import play.api.libs.json.{ Json, Reads }
import usermanager.domain.aggregates.sessionuser.SessionUser

case class SessionRead(
  sessionId: String,
  userName: String
) extends {

  def toDomain: SessionUser = {
    SessionUser(
      id = sessionId,
      userName = userName,
      versionNo = 0
    )
  }

}

object SessionRead {

  implicit val reads: Reads[SessionRead] = Json.reads[SessionRead]

}
