package commands

import usermanager.domain.aggregates.user.write.UserWrite
import usermanager.domain.helpers.HashHelper

case class UserCreateCommand(
  userId: String,
  userName: String,
  email: String,
  password: String
) {

  def toEntity(implicit hashHelper: HashHelper): UserWrite = ???

}
