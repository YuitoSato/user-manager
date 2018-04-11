package usermanager.domain.aggregates.user.write

import usermanager.domain.transaction.sync.SyncTransaction

trait UserWriteRepository {

  def create(user: UserWrite): SyncTransaction[Unit]

//  def update(user: UserWrite): SyncTransaction[Unit]

}
