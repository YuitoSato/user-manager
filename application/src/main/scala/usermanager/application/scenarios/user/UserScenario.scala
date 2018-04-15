package usermanager.application.scenarios.user

import usermanager.application.scenarios.ScenarioBase
import usermanager.application.services.user.UserService
import usermanager.domain.aggregates.user.User
import usermanager.domain.result.Result
import usermanager.domain.types.{ Email, Id }

trait UserScenario extends ScenarioBase {

  val userService: UserService

  def findById(userId: Id[User]): Result[User] = {
    userService.findById(userId).run
  }

  def findByEmail(email: Email[User]): Result[User] = {
    userService.findByEmail(email).run
  }

  def create(user: User): Result[Unit] = {
    (for {
      _ <-  userService.assertEmailNotExists(user.email)
      _ <-  userService.create(user)
    } yield ()).run
  }

}
