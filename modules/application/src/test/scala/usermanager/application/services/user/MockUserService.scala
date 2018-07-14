package usermanager.application.services.user
import usermanager.domain.aggregates.user.{ MockUserRepository, UserRepository }
import usermanager.domain.transaction.MockTransactionBuilder
import usermanager.lib.error.transaction.TransactionBuilder

class MockUserService extends UserService {

  override val userRepository: UserRepository = new MockUserRepository
  override implicit val transactionBuilder: TransactionBuilder = new MockTransactionBuilder

}
