package usermanager.domain.aggregates.user.read

import usermanager.domain.transaction.sync.SyncTransaction
import usermanager.domain.types.{ Email, Id }

trait UserReadRepository {

  def find(userId: Id[UserRead]): SyncTransaction[Option[UserRead]]

  def findByEmail(email: Email[UserRead]): SyncTransaction[Option[UserRead]]

}
