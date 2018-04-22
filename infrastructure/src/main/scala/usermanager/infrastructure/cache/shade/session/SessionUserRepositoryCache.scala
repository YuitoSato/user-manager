package usermanager.infrastructure.cache.shade.session

import javax.inject.Inject

import usermanager.domain.aggregates.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction
import usermanager.domain.transaction.delete.Deleted
import usermanager.domain.types.Id
import usermanager.infrastructure.cache.shade.ShadeCache
import usermanager.infrastructure.cache.shade.transaction.ShadeTransaction

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class SessionUserRepositoryCache @Inject()(
  implicit ec: ExecutionContext
) extends SessionUserRepository with ToEitherOps {

  val cache: ShadeCache = new ShadeCache

  override def find(sessionId: Id[SessionUser]): Transaction[Option[SessionUser]] = {
    ShadeTransaction.from { () =>
      cache.getJson[SessionRead](sessionId).map(_.map(_.toDomain))
    }
  }

  override def create(session: SessionUser): Transaction[Unit] = {
    ShadeTransaction.from { () =>
      cache.setJson(session.id, SessionWrite.fromDomain(session), 24 hour)
    }
  }

  override def delete(sessionId: Id[SessionUser]): Transaction[Deleted] = {
    ShadeTransaction.from { () =>
      cache.delete(sessionId).map(Deleted(_))
    }
  }

}
