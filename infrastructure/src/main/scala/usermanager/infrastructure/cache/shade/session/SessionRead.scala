package usermanager.infrastructure.cache.shade.session

import play.api.libs.json.{ Json, Reads }
import usermanager.domain.sessionuser.SessionUser

case class SessionRead(
  sessionId: String,
  versionNo: Int
) {

  def toDomain: SessionUser = SessionUser(sessionId, versionNo)

}

object SessionRead {

  implicit val reads: Reads[SessionRead] = Json.reads[SessionRead]

}
