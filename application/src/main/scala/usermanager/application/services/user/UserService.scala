package usermanager.application.services.user

import javax.inject.{ Inject, Named }

import usermanager.domain.aggregates.user.read.{ UserRead, UserReadRepository }
import usermanager.domain.aggregates.user.write.{ UserWrite, UserWriteRepository }
import usermanager.domain.error.{ DomainError, ErrorHandler }
import usermanager.domain.transaction.{ Transaction, TransactionBuilder }
import usermanager.domain.types.{ Email, Id }

import scala.concurrent.ExecutionContext

class UserService @Inject()(
  @Named("rdb.scalikejdbc") userReadRepository: UserReadRepository,
  @Named("rdb.scalikejdbc") userWriteRepository: UserWriteRepository,
  @Named("rdb.scalikejdbc") implicit val transactionBuilder: TransactionBuilder
)(
  implicit ec: ExecutionContext,
) extends ErrorHandler {

  def findById(userId: Id[UserRead]): Transaction[UserRead] = {
    userReadRepository.find(userId) ifNotExists DomainError.NotFound("UserRead", userId)
  }

  def findByEmail(email: Email[UserRead]): Transaction[UserRead] = {
    userReadRepository.findByEmail(email) ifNotExists DomainError.NotFound("Session", email.value)
  }

  def create(user: UserWrite): Transaction[Unit] = {
    userWriteRepository.create(user)
  }

}
