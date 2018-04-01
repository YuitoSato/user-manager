package usermanager.domain.user.write

import usermanager.domain.transaction.{ ReadWriteTransaction, Task }

trait UserWriteRepository {

  def create(user: UserWrite): Task[ReadWriteTransaction, Unit]

  def update(user: UserWrite): Task[ReadWriteTransaction, Unit]

}
