package usermanager.domain.sessionuser

import usermanager.domain.types.Id

import scala.concurrent.Future

trait SessionUserRepository {

  def find(sessionId: Id[SessionUser]): Future[Option[SessionUser]]

  def awaitFind(sessionId: Id[SessionUser]): Option[SessionUser]

  def create(session: SessionUser): Future[Unit]

  def delete(sessionId: Id[SessionUser]): Future[Boolean]

}
