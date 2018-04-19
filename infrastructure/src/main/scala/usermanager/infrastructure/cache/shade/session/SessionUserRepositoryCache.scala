package usermanager.infrastructure.cache.shade.session

import javax.inject.Inject

import usermanager.domain.aggregates.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction
import usermanager.domain.transaction.delete.Deleted
import usermanager.domain.types.Id
import usermanager.infrastructure.cache.shade.ShadeCache
import usermanager.infrastructure.cache.shade.transaction.AsyncShadeTransaction

import scala.concurrent.duration._
import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ \/, \/- }

class SessionUserRepositoryCache @Inject()(
  implicit ec: ExecutionContext
) extends SessionUserRepository with ToEitherOps {

  val cache: ShadeCache = new ShadeCache

  override def find(sessionId: Id[SessionUser]): Transaction[Option[SessionUser]] = {
    val future: Future[DomainError \/ Option[SessionUser]] = cache.getJson[SessionRead](sessionId).map(_.map(_.toDomain)).map(\/-(_))
    AsyncShadeTransaction(future.et)
  }

  override def create(session: SessionUser): Transaction[Unit] = {
    val future: Future[DomainError \/ Unit] = cache.setJson(session.id, SessionWrite.fromDomain(session), 24 hour).map(\/-(_))
    AsyncShadeTransaction(future.et)
  }

  override def delete(sessionId: Id[SessionUser]): Transaction[Deleted] = {
    val future: Future[DomainError \/ Deleted] = cache.delete(sessionId).map(\/-(_))
    AsyncShadeTransaction(future.et)
  }

}
