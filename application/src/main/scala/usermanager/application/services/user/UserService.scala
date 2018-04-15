package usermanager.application.services.user

import usermanager.domain.aggregates.user.{ User, UserRepository }
import usermanager.domain.error.{ DomainError, ErrorHandler }
import usermanager.domain.transaction.{ Transaction, TransactionBuilder }
import usermanager.domain.types.{ Email, Id }

trait UserService extends ErrorHandler {

  val userRepository: UserRepository
  implicit val transactionBuilder: TransactionBuilder

  def findById(userId: Id[User]): Transaction[User] = {
    userRepository.find(userId) ifNotExists DomainError.NotFound(User.TYPE, userId)
  }

  def findByEmail(email: Email[User]): Transaction[User] = {
    userRepository.findByEmail(email) ifNotExists DomainError.EmailNotFound(email)
  }

  def assertEmailNotExists(email: Email[User]): Transaction[Unit] = {
    userRepository.findByEmail(email) ifExists DomainError.EmailExists(email)
  }

  def create(user: User): Transaction[Unit] = {
    userRepository.create(user)
  }

}
