package usermanager.domain.session

import usermanager.domain.types.Id
import usermanager.domain.user.User

case class Session(
  sessionId: Id[Session]
) {

  def userId: Id[User] = sessionId.asInstanceOf[Id[User]]

}
