package usermanager.domain.aggregates.user

import usermanager.domain.transaction.Transaction
import usermanager.domain.types.{ Email, Id }

trait UserRepository {

  def find(userId: Id[User]): Transaction[Option[User]]

  def findByEmail(email: Email[User]): Transaction[Option[User]]

  def create(user: User): Transaction[Unit]

}
