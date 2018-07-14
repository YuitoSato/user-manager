package controllers.security

import error.InterfaceError
import javax.inject.Inject
import play.api.mvc._
import scalaz.std.FutureInstances
import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, \/- }
import syntax.ToResultOps
import usermanager._
import usermanager.application.scenarios.session.SessionScenario
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.syntax.ToEitherOps
import usermanager.lib.result.{ AsyncResult, ResultBuilder }

import scala.concurrent.{ ExecutionContext, Future }

case class SecureAction @Inject() (
  sessionScenario: SessionScenario
)(
  implicit val controllerComponents: ControllerComponents,
  implicit val ec: ExecutionContext,
  implicit val resultBuilder: ResultBuilder
) extends ToResultOps with ToEitherOps with ToOptionOps with BaseControllerHelpers with FutureInstances {

  def findUserBySession(req: Request[_]): lib.result.Result[SessionUser] = {
    for {
      key <- resultBuilder.build(req.session.get("session") \/> InterfaceError.SessionKeyNotFound)
      user <- sessionScenario.findById(key)
    } yield user
  }

  def async(requestHandler: SecureRequest[AnyContent] => Future[Result]): Action[AnyContent] = {
    async(controllerComponents.parsers.anyContent)(requestHandler)
  }

  def async[A](bodyParser: BodyParser[A])(requestHandler: SecureRequest[A] => Future[Result]): Action[A] = {
    controllerComponents.actionBuilder.async(bodyParser) { req =>
      // TODO asInstanceOfは使いたくない
      findUserBySession(req).asInstanceOf[AsyncResult[SessionUser]].value.run.flatMap {
        case \/-(user) => requestHandler(SecureRequest(user, req))
        case -\/(e) => Future.successful(e.toResult)
      }
    }
  }

}
