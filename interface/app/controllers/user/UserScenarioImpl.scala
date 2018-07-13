package controllers.user

import javax.inject.{ Inject, Singleton }
import usermanager.application.scenarios.user.UserScenario
import usermanager.application.services.user.UserService

@Singleton
class UserScenarioImpl @Inject()(
  val userService: UserService,
) extends UserScenario
