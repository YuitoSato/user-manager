package commands

import play.api.libs.json._
import usermanager.domain.aggregates.user.User
import usermanager.domain.helpers.{ HashHelper, UUIDGenerator }
import usermanager.domain.types.Status

case class UserCreateCommand(
  userName: String,
  email: String,
  password: String
) {

  def toEntity(implicit hashHelper: HashHelper, uuidGenerator: UUIDGenerator): User = {
    User(
      id = uuidGenerator.createId,
      userName = userName,
      email = email,
      password = hashHelper.encrypt(password),
      status = Status.Enable,
      versionNo = 0
    )
  }

  def withError(implicit hashHelper: HashHelper, uuidGenerator: UUIDGenerator): User = {
    User(
      id = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      userName = userName,
      email = email,
      password = hashHelper.encrypt(password),
      status = Status.Enable,
      versionNo = 0
    )
  }

}

object UserCreateCommand {

  implicit def userCreateCommandReads: Reads[UserCreateCommand] = new Reads[UserCreateCommand] {
    def reads(json: JsValue): JsResult[UserCreateCommand] = {
      for {
        userName <- (json \ "userName").validate[String]
        _ <- if (userName.length <= 50) JsSuccess(()) else  JsError(JsPath \ "userName", "invalid format")
        email <- (json \ "email").validate[String]
        _ <- if (email.length <= 100) JsSuccess(()) else JsError(JsPath \ "email", "invalid format")
        password <- (json \ "password").validate[String]
        _ <- if (password.length > 7) JsSuccess(()) else JsError(JsPath \ "password", "invalid format")
      } yield UserCreateCommand(
        userName = userName,
        email = email,
        password = password)
    }
  }

}

