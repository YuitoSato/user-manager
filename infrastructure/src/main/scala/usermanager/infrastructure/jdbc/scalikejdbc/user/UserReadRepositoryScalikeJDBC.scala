package usermanager.infrastructure.jdbc.scalikejdbc.user

import usermanager.domain.transaction.{ ReadTransaction, Task }
import usermanager.domain.types.{ Email, Id }
import usermanager.domain.user.read.{ UserRead, UserReadRepository }
import usermanager.infrastructure.jdbc.scalikejdbc.transaction.{ ScalikeJDBCTransactionBuilder, ScalikeJDBCTransactionRunner }
import scalikejdbc._

class UserReadRepositoryScalikeJDBC extends UserReadRepository
  with ScalikeJDBCTransactionBuilder
  with ScalikeJDBCTransactionRunner
  with RichUserScalikeJDBC
{

  override def find(userId: Id[UserRead]): Task[ReadTransaction, Option[UserRead]] = {
    ask.map { implicit session =>
      Users.find(userId.toString).map(_.toDomain)
    }
  }

  override def findByEmail(email: Email[UserRead]): Task[ReadTransaction, Option[UserRead]] = {
    ask.map { implicit session =>
      Users.findBy(sqls"where email = $email").map(_.toDomain)
    }
  }

}
