package usermanager.domain.user.read

import usermanager.domain.transaction.async.AsyncTransaction
import usermanager.domain.types.{ Email, Id }

trait UserReadRepository {

  def find(userId: Id[UserRead]): AsyncTransaction[Option[UserRead]]

  def findByEmail(email: Email[UserRead]): AsyncTransaction[Option[UserRead]]

}
