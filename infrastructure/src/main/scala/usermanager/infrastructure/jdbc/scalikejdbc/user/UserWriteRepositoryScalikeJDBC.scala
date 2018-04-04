package usermanager.infrastructure.jdbc.scalikejdbc.user

import usermanager.domain.user.write.{ UserWrite, UserWriteRepository }
import usermanager.infrastructure.jdbc.scalikejdbc.transaction.{ ScalikeJDBCTransactionBuilder, ScalikeJDBCTransactionRunner }

class UserWriteRepositoryScalikeJDBC extends UserWriteRepository
  with ScalikeJDBCTransactionBuilder
  with ScalikeJDBCTransactionRunner
  with RichUserScalikeJDBC
{
  override def create(user: UserWrite) = ???

  override def update(user: UserWrite) = ???

}
