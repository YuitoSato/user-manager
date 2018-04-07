package commands

import play.api.libs.json.{ Json, Reads }

case class LoginCommand(
  email: String,
  password: String
)

object LoginCommand {

  implicit val loginCommandReads: Reads[LoginCommand] = Json.reads[LoginCommand]

}
