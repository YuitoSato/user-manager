package usermanager.application.services.user

import usermanager.application.services.ServiceBase
import usermanager.domain.aggregates.user.{ User, UserRepository }
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.Transaction
import usermanager.domain.types.{ Email, Id }

trait UserService extends ServiceBase {

  val userRepository: UserRepository

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
