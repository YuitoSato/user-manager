package usermanager.application.services.user
import usermanager.domain.aggregates.user.{ MockUserRepository, UserRepository }
import usermanager.domain.transaction.{ MockTransactionBuilder, TransactionBuilder }

class MockUserService extends UserService {

  override val userRepository: UserRepository = new MockUserRepository
  override implicit val transactionBuilder: TransactionBuilder = new MockTransactionBuilder

}
