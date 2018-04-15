package usermanager.application.services.user
import usermanager.domain.aggregates.user.read.{ MockUserReadRepository, UserReadRepository }
import usermanager.domain.aggregates.user.write.{ MockUserWriteRepository, UserWriteRepository }
import usermanager.domain.transaction.{ MockTransactionBuilder, TransactionBuilder }

class MockUserService extends UserService {

  override val userReadRepository: UserReadRepository = new MockUserReadRepository
  override val userWriteRepository: UserWriteRepository = new MockUserWriteRepository
  override implicit val transactionBuilder: TransactionBuilder = new MockTransactionBuilder

}
