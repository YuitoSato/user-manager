package usermanager.application.scenarios.user

import usermanager.application.services.user.MockUserService
import usermanager.domain.transaction.MockTransactionRunner

class MockUserScenario extends UserScenario {

  override val userService = new MockUserService
  override implicit val transactionRunner: MockTransactionRunner =
    new MockTransactionRunner

}
