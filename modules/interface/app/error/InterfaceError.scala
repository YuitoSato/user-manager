package error

import play.api.libs.json.JsError
import usermanager.lib.error.Error

trait InterfaceError extends Error

object InterfaceError {

  case class JsonReadsError(e: JsError) extends Error {
    override val code: String = "error.jsonReads"
    override val message: String =  e.errors.mkString
  }

  case object SessionKeyNotFound extends Error {
    override val code: String = "error.sessionKeyNotFound"
    override val message: String =  "session key is not found."
  }

}
