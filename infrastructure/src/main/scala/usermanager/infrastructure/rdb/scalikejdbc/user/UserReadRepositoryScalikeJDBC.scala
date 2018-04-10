package usermanager.infrastructure.rdb.scalikejdbc.user

import javax.inject.Inject

import scalikejdbc._
import usermanager.domain.types.{ Email, Id }
import usermanager.domain.aggregates.user.read.{ UserRead, UserReadRepository }
import usermanager.infrastructure.rdb.scalikejdbc.transaction.ScalikeJDBCTransaction

import scalaz.\/

class UserReadRepositoryScalikeJDBC @Inject() extends UserReadRepository with RichUserScalikeJDBC {

  override def find(userId: Id[UserRead]): ScalikeJDBCTransaction[Option[UserRead]] = {
    def run(dbSession: DBSession) = \/.right(Users.find(userId)(dbSession).map(_.toDomain))
    ScalikeJDBCTransaction(run)
  }

  override def findByEmail(email: Email[UserRead]): ScalikeJDBCTransaction[Option[UserRead]] = {
    def run(dBSession: DBSession) = \/.right(Users.findBy(sqls"where email = $email")(dBSession).map(_.toDomain))
    ScalikeJDBCTransaction(run)
  }

}
