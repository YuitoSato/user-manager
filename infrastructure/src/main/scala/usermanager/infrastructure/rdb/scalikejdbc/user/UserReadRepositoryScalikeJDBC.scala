package usermanager.infrastructure.rdb.scalikejdbc.user

import javax.inject.Inject

import scalikejdbc._
import usermanager.domain.types.{ Email, Id }
import usermanager.domain.aggregates.user.read.{ UserRead, UserReadRepository }
import usermanager.infrastructure.rdb.scalikejdbc.transaction.ScalikeJDBCTransaction

import scalaz.\/

class UserReadRepositoryScalikeJDBC @Inject() extends UserReadRepository with RichUserScalikeJDBC {

  override def find(userId: Id[UserRead]): ScalikeJDBCTransaction[Option[UserRead]] = {
    def exec(dbSession: DBSession) = {
      \/.right(
        Users.find(userId)(dbSession).map(_.toDomain)
      )
    }
    ScalikeJDBCTransaction(exec)

  }

  override def findByEmail(email: Email[UserRead]): ScalikeJDBCTransaction[Option[UserRead]] = {
    def exec(dBSession: DBSession) = {
      \/.right(
        Users.findBy(sqls.eq(Users.syntax("u").email, email.value))(dBSession).map(_.toDomain)
      )
    }
    ScalikeJDBCTransaction(exec)
  }

}
