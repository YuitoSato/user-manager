package usermanager.domain.aggregates.user.read

import usermanager.domain.transaction.Transaction
import usermanager.domain.types.{ Email, Id }

trait UserReadRepository {

  def find(userId: Id[UserRead]): Transaction[Option[UserRead]]

  def findByEmail(email: Email[UserRead]): Transaction[Option[UserRead]]

}
