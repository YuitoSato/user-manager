package controllers.security

import play.api.mvc.{ Request, WrappedRequest }

case class SecureRequest[A](
  user: User,
  request: Request[A]
) extends WrappedRequest[A](request)
