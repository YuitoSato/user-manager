package usermanager.infrastructure.jdbc.scalikejdbc.user

import usermanager.domain.user.write.{ UserWrite, UserWriteRepository }
import usermanager.infrastructure.jdbc.scalikejdbc.transaction.{ ScalikeJDBCTaskBuilder, ScalikeJDBCTaskRunner }

class UserWriteRepositoryScalikeJDBC extends UserWriteRepository
  with ScalikeJDBCTaskBuilder
  with ScalikeJDBCTaskRunner
  with RichUserScalikeJDBC
{
  override def create(user: UserWrite) = ???

  override def update(user: UserWrite) = ???

}
