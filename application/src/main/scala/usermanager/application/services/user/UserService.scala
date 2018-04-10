package usermanager.application.services.user

import javax.inject.{ Inject, Named }

import usermanager.domain.error.{ DomainError, ErrorHandler }
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionBuilder }
import usermanager.domain.types.{ Email, Id }
import usermanager.domain.aggregates.user.read.{ UserRead, UserReadRepository }
import usermanager.domain.aggregates.user.write.{ UserWrite, UserWriteRepository }

import scala.concurrent.ExecutionContext

class UserService @Inject()(
  @Named("rdb.scalikejdbc") userReadRepository: UserReadRepository,
  @Named("rdb.scalikejdbc") userWriteRepository: UserWriteRepository,
  @Named("rdb.scalikejdbc") implicit val syncTransactionBuilder: SyncTransactionBuilder
)(
  implicit ec: ExecutionContext,
) extends ErrorHandler {

  def findById(userId: Id[UserRead]): SyncTransaction[UserRead] = {
    userReadRepository.find(userId) ifNotExists DomainError.NotFound("UserRead", userId)
  }

  def findByEmail(email: Email[UserRead]): SyncTransaction[UserRead] = {
    userReadRepository.findByEmail(email) ifNotExists DomainError.NotFound("Session", email.value)
  }

  def create(user: UserWrite): SyncTransaction[Unit] = {
    userWriteRepository.create(user)
  }

}
