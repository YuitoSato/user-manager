package usermanager.infrastructure.cache.shade.session

import play.api.libs.json.{ Json, Writes }
import usermanager.domain.session.Session

case class SessionWrite(
  sessionId: String
)

object SessionWrite {

  implicit val writes: Writes[SessionWrite] = Json.writes[SessionWrite]

  def fromDomain(domain: Session): SessionWrite = SessionWrite(domain.id)

}
