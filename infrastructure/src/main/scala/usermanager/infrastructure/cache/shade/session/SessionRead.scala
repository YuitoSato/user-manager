package usermanager.infrastructure.cache.shade.session

import play.api.libs.json.{ Json, Reads }
import usermanager.domain.session.Session

class SessionRead(
  sessionId: String,
  versionNo: Int
) {

  def toDomain: Session = Session(sessionId, versionNo)

}

object SessionRead {

  implicit val reads: Reads[SessionWrite] = Json.reads[SessionWrite]

}
