package error

import play.api.libs.json.JsError
import usermanager.domain.error.AbstractError

sealed trait InterfaceError extends AbstractError

object InterfaceError {

  case class JsonError(error: JsError) extends InterfaceError {
    val code = "error.json"
    val message = s"Json error occured. error=$error"
  }

}
