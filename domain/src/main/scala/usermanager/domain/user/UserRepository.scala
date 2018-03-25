package usermanager.domain.user

import usermanager.domain.transaction.Transaction
import usermanager.domain.types.{ Email, Id }

trait UserRepository {

  def findById(userId: Id[User]): Transaction[Option[User]]

  def findByEmail(email: Email[User]): Transaction[Option[User]]

  def create(user: User): Transaction[Unit]

  def update(user: User): Transaction[Unit]

}
