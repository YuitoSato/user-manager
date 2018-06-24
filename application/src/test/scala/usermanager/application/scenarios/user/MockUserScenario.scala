package usermanager.application.scenarios.user

import usermanager.application.services.user.MockUserService

class MockUserScenario extends UserScenario {

  override val userService = new MockUserService

}
