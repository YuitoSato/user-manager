package usermanager.infrastructure.cache.shade.session

import play.api.libs.json.{ Json, Writes }
import usermanager.domain.aggregates.sessionuser.SessionUser

case class SessionWrite(
  sessionId: String,
  userName: String
)

object SessionWrite {

  implicit val writes: Writes[SessionWrite] = Json.writes[SessionWrite]

  def fromDomain(domain: SessionUser): SessionWrite = {
    SessionWrite(
      sessionId = domain.id,
      userName = domain.userName
    )
  }

}
