package usermanager.application.services.session
import usermanager.domain.aggregates.session.MockSessionUserRepository
import usermanager.domain.aggregates.sessionuser.SessionUserRepository
import usermanager.domain.transaction.{ MockTransactionBuilder, TransactionBuilder }

class MockSessionService extends SessionService {

  override val sessionRepository: SessionUserRepository = new MockSessionUserRepository
  override implicit val transactionBuilder: TransactionBuilder = new MockTransactionBuilder

}
