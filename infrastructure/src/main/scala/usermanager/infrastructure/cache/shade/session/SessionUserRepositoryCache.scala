package usermanager.infrastructure.cache.shade.session

import usermanager.domain.error.DomainError
import usermanager.domain.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.async.AsyncTransaction
import usermanager.domain.transaction.sync.SyncTransaction
import usermanager.domain.types.Id
import usermanager.infrastructure.cache.shade.ShadeCache
import usermanager.infrastructure.cache.shade.transaction.ShadeTransaction

import scala.concurrent.duration._
import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ \/, \/- }

class SessionUserRepositoryCache(
  implicit ec: ExecutionContext
) extends SessionUserRepository with ToEitherOps {

  val cache: ShadeCache = new ShadeCache

  override def find(sessionId: Id[SessionUser]): AsyncTransaction[Option[SessionUser]] = {
    val future: Future[DomainError \/ Option[SessionUser]] = cache.getJson[SessionRead](sessionId).map(_.map(_.toDomain)).map(\/-(_))
    ShadeTransaction(future.et)
  }

  override def awaitFind(sessionId: Id[SessionUser]): SyncTransaction[Option[SessionUser]] = {
    ???
    //    cache.awaitGetJson[SessionRead](sessionId).map(_.toDomain)
  }

  override def create(session: SessionUser): AsyncTransaction[Unit] = {
    val future: Future[DomainError \/ Unit] = cache.setJson(session.id, SessionWrite.fromDomain(session), 24 hour).map(\/-(_))
    ShadeTransaction(future.et)
  }

  override def delete(sessionId: Id[SessionUser]): AsyncTransaction[Boolean] = {
    val future: Future[DomainError \/ Boolean] = cache.delete(sessionId).map(\/-(_))
    ShadeTransaction(future.et)
  }

}
