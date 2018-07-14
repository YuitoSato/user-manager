package usermanager.application.services.user

import usermanager.application.services.ServiceBase
import usermanager.domain.aggregates.user.{ User, UserRepository }
import usermanager.domain.types.{ Email, Id }
import usermanager.lib.error.Error
import usermanager.lib.error.transaction.Transaction

trait UserService extends ServiceBase {

  val userRepository: UserRepository

  def findById(userId: Id[User]): Transaction[User] = {
    userRepository.find(userId) assertNotExists Error.NotFound(User.TYPE, userId)
  }

  def findByEmail(email: Email[User]): Transaction[User] = {
    userRepository.findByEmail(email) assertNotExists Error.EmailNotFound(email)
  }

  def assertEmailNotExists(email: Email[User]): Transaction[Unit] = {
    userRepository.findByEmail(email) assertExists Error.EmailExists(email)
  }

  def create(user: User): Transaction[Unit] = {
    userRepository.create(user)
  }

}
