package usermanager.infrastructure.jdbc.scalikejdbc.user

import usermanager.domain.transaction.{ ReadTransaction, Task, Transaction }
import usermanager.domain.types.{ Email, Id }
import usermanager.domain.user.{ User, UserRepository }
import usermanager.infrastructure.jdbc.scalikejdbc.transaction.{ ScalikeJDBCTaskBuilder, ScalikeJDBCTaskRunner }

class UserRepositoryScalikeJDBC extends UserRepository
  with ScalikeJDBCTaskBuilder
  with ScalikeJDBCTaskRunner
  with RichUserScalikeJDBC
{

  override def findById(userId: Id[User]): Task[ReadTransaction, Option[User]] = {
    ask.map { implicit session =>
      Users.find(userId.toString).map(_.toDomain)
    }
  }

  override def findByEmail(email: Email[User]) = ???

  override def create(user: User) = {
    ask.map { implicit session =>
      Users.
    }
  }

  override def update(user: User) = ???
}
