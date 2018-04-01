package usermanager.domain.user.read

import usermanager.domain.transaction.{ ReadTransaction, Task }
import usermanager.domain.types.{ Email, Id }

trait UserReadRepository {

  def find(userId: Id[UserRead]): Task[ReadTransaction, Option[UserRead]]

  def findByEmail(email: Email[UserRead]): Task[ReadTransaction, Option[UserRead]]

}
