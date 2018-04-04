package usermanager.domain.user.write

import usermanager.domain.transaction.async.AsyncTransaction

trait UserWriteRepository {

  def create(user: UserWrite): AsyncTransaction[Unit]

  def update(user: UserWrite): AsyncTransaction[Unit]

}
