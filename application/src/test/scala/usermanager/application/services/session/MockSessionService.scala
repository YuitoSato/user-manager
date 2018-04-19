package usermanager.application.services.session
import usermanager.domain.aggregates.sessionuser.SessionUserRepository
import usermanager.domain.transaction.TransactionBuilder

class MockSessionService extends SessionService {

  override val sessionRepository: SessionUserRepository = _
  override implicit val transactionBuilder: TransactionBuilder = _

}
