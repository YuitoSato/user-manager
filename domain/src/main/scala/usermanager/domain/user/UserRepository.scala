package usermanager.domain.user

import usermanager.domain.transaction.{ ReadTransaction, ReadWriteTransaction, Task }
import usermanager.domain.types.{ Email, Id }

trait UserRepository {

  def findById(userId: Id[User]): Task[ReadTransaction, Option[User]]

  def findByEmail(email: Email[User]): Task[ReadTransaction, Option[User]]]]

  def create(user: User): Task[ReadWriteTransaction, Unit]

  def update(user: User): Task[ReadWriteTransaction, Unit]

}
