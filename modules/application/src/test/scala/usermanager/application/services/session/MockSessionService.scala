package usermanager.application.services.session
import usermanager.domain.aggregates.session.MockSessionRepository
import usermanager.domain.aggregates.sessionuser.SessionRepository
import usermanager.domain.transaction.MockTransactionBuilder
import usermanager.lib.error.transaction.TransactionBuilder

class MockSessionService extends SessionService {

  override val sessionRepository: SessionRepository = new MockSessionRepository
  override implicit val transactionBuilder: TransactionBuilder = new MockTransactionBuilder

}
