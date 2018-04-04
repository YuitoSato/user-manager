package usermanager.domain.sessionuser

import usermanager.domain.transaction.async.AsyncTransaction
import usermanager.domain.types.Id

import scala.concurrent.Future

trait SessionUserRepository {

  def find(sessionId: Id[SessionUser]): AsyncTransaction[Option[SessionUser]]

  def awaitFind(sessionId: Id[SessionUser]): AsyncTransaction[Option[SessionUser]]

  def create(session: SessionUser): AsyncTransaction[Future[Unit]]

  def delete(sessionId: Id[SessionUser]): AsyncTransaction[Future[Boolean]]

}
