package usermanager.infrastructure.cache.shade.session

import play.api.libs.json.{ Json, Reads }
import usermanager.domain.session.Session

class SessionRead(
  sessionId: String
) {

  def toDomain: Session = Session(sessionId)

}

object SessionRead {

  implicit val reads: Reads[SessionWrite] = Json.reads[SessionWrite]

}
