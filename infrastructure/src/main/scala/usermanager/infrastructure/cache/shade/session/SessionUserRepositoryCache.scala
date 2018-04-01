package usermanager.infrastructure.cache.shade.session

import usermanager.domain.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.types.Id
import usermanager.infrastructure.cache.shade.ShadeCache

import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.duration._
import scalaz.syntax.ToEitherOps

class SessionUserRepositoryCache (
  implicit ec: ExecutionContext
) extends SessionUserRepository with ToEitherOps {

  val cache: ShadeCache = new ShadeCache

  override def find(sessionId: Id[SessionUser]): Future[Option[SessionUser]] = cache.getJson[SessionRead](sessionId).map(_.map(_.toDomain))

  override def awaitFind(sessionId: Id[SessionUser]): Option[SessionUser] = cache.awaitGetJson[SessionRead](sessionId).map(_.toDomain)

  override def create(session: SessionUser): Future[Unit] = cache.setJson(session.id, SessionWrite.fromDomain(session), 24 hour)

  override def delete(sessionId: Id[SessionUser]): Future[Boolean] = cache.delete(sessionId)

}
