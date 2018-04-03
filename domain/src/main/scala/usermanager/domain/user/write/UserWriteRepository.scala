package usermanager.domain.user.write

import usermanager.domain.transaction.Transaction

trait UserWriteRepository {

  def create(user: UserWrite): Transaction[Unit]

  def update(user: UserWrite): Transaction[Unit]

}
