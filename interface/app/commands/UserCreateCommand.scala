package commands

import play.api.libs.json.{ Json, Reads }
import usermanager.domain.aggregates.user.write.UserWrite
import usermanager.domain.helpers.UUIDGenerator
import usermanager.domain.helpers.HashHelper
import usermanager.domain.types.Status

case class UserCreateCommand(
  userName: String,
  email: String,
  password: String
) {

  def toEntity(implicit hashHelper: HashHelper, uuidGenerator: UUIDGenerator): UserWrite = {
    UserWrite(
      id = uuidGenerator.createId,
      userName = userName,
      email = email,
      password = hashHelper.encrypt(password),
      status = Status.Enable,
      versionNo = 0
    )
  }

}

object UserCreateCommand {

  implicit val UserCreateCommandReads: Reads[UserCreateCommand] = Json.reads[UserCreateCommand]

}

