package usermanager.application.services.scenario

import usermanager.application.scenarios.user.UserScenario
import usermanager.application.services.user.MockUserService
import usermanager.domain.transaction.MockTransactionRunner

class MockUserScenario extends UserScenario {

  override val userService = new MockUserService
  override implicit val transactionRunner: MockTransactionRunner =
    new MockTransactionRunner

}
