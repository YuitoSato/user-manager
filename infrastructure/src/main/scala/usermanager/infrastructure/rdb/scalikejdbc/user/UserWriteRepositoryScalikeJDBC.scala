package usermanager.infrastructure.rdb.scalikejdbc.user

import usermanager.domain.aggregates.user.write.{ UserWrite, UserWriteRepository }
import usermanager.infrastructure.rdb.scalikejdbc.transaction.{ ScalikeJDBCTransactionBuilder, ScalikeJDBCTransactionRunner }

class UserWriteRepositoryScalikeJDBC extends UserWriteRepository
  with RichUserScalikeJDBC
{
  override def create(user: UserWrite) = ???

  override def update(user: UserWrite) = ???

}
