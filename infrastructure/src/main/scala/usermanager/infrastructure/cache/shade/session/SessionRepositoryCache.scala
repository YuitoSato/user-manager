package usermanager.infrastructure.cache.shade.session

import usermanager.domain.session.{ Session, SessionRepository }
import usermanager.domain.types.Id
import usermanager.infrastructure.cache.shade.ShadeCache

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Try
import scalaz.syntax.ToEitherOps

class SessionRepositoryCache (
  implicit ec: ExecutionContext
) extends SessionRepository[Try] with ToEitherOps {

  val cache: ShadeCache = new ShadeCache

  override def findById(sessionId: Id[Session]) = Try { cache.getJson[SessionRead](sessionId).map(_.toDomain) }

  override def create(session: Session) = Try { cache.setJson(session.sessionId, SessionWrite.fromDomain(session), 24 hour) }

  override def delete(sessionId: Id[Session]) = Try { cache.delete(sessionId) }

}
