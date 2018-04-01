package usermanager.infrastructure.cache.shade.session

import usermanager.domain.session.{ Session, SessionRepository }
import usermanager.domain.types.Id
import usermanager.infrastructure.cache.shade.ShadeCache

import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.duration._
import scalaz.syntax.ToEitherOps

class SessionRepositoryCache (
  implicit ec: ExecutionContext
) extends SessionRepository with ToEitherOps {

  val cache: ShadeCache = new ShadeCache

  override def find(sessionId: Id[Session]): Future[Option[Session]] = cache.getJson[SessionRead](sessionId).map(_.map(_.toDomain))

  override def awaitFind(sessionId: Id[Session]): Option[Session] = cache.awaitGetJson[SessionRead](sessionId).map(_.toDomain)

  override def create(session: Session): Future[Unit] = cache.setJson(session.id, SessionWrite.fromDomain(session), 24 hour)

  override def delete(sessionId: Id[Session]): Future[Boolean] = cache.delete(sessionId)

}
