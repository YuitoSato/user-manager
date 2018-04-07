package usermanager.application.services.user

import com.google.inject.Inject
import usermanager.domain.error.{ DomainError, ErrorHandler }
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionBuilder }
import usermanager.domain.types.Email
import usermanager.domain.aggregates.user.read.{ UserRead, UserReadRepository }
import usermanager.domain.aggregates.user.write.UserWriteRepository

import scala.concurrent.ExecutionContext


class UserService @Inject()(
  userReadRepository: UserReadRepository,
  userWriteRepository: UserWriteRepository
)(
  implicit ec: ExecutionContext,
  implicit val syncTransactionBuilder: SyncTransactionBuilder
) extends ErrorHandler {

  def findByEmail(email: Email[UserRead]): SyncTransaction[UserRead] = {
    userReadRepository.findByEmail(email) ifNotExists DomainError.NotFound("Session", email.value)
  }

}
