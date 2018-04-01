package usermanager.infrastructure.cache.shade.session

import play.api.libs.json.{ Json, Writes }
import usermanager.domain.sessionuser.SessionUser

case class SessionWrite(
  sessionId: String
)

object SessionWrite {

  implicit val writes: Writes[SessionWrite] = Json.writes[SessionWrite]

  def fromDomain(domain: SessionUser): SessionWrite = SessionWrite(domain.id)

}
