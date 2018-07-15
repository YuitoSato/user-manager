package usermanager.application.services.user

import usermanager.application.error.ApplicationError
import usermanager.application.services.ServiceBase
import usermanager.domain.aggregates.user.{ User, UserRepository }
import usermanager.domain.types.{ Email, Id }
import usermanager.lib.transaction.Transaction

trait UserService extends ServiceBase {

  val userRepository: UserRepository

  def findById(userId: Id[User]): Transaction[User] = {
    userRepository.find(userId) assertExists ApplicationError.NotFound(User.TYPE, userId)
  }

  def findByEmail(email: Email[User]): Transaction[User] = {
    userRepository.findByEmail(email) assertExists ApplicationError.EmailNotFound
  }

  def assertEmailNotExists(email: Email[User]): Transaction[Unit] = {
    userRepository.findByEmail(email) assertNotExists ApplicationError.EmailExists
  }

  def create(user: User): Transaction[Unit] = {
    userRepository.create(user)
  }

}
