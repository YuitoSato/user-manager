package usermanager.application.scenarios.session

import usermanager.application.services.session.{ MockSessionService, SessionService }
import usermanager.domain.transaction.{ MockTransactionRunner, TransactionRunner }

class MockSessionScenario extends SessionScenario {

  override val sessionService: SessionService = new MockSessionService
  override implicit val transactionRunner: TransactionRunner = new MockTransactionRunner

}
