package usermanager.application.scenarios.session

import usermanager.application.services.session.{ MockSessionService, SessionService }

class MockSessionScenario extends SessionScenario {

  override val sessionService: SessionService = new MockSessionService

}
