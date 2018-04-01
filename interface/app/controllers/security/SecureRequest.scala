package controllers.security

import play.api.mvc.{ Request, WrappedRequest }
import usermanager.domain.sessionuser.SessionUser

case class SecureRequest[A](
  sessionUser: SessionUser,
  request: Request[A]
) extends WrappedRequest[A](request)
