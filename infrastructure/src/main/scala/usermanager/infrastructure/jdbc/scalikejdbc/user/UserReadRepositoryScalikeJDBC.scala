package usermanager.infrastructure.jdbc.scalikejdbc.user

import scalikejdbc._
import usermanager.domain.types.{ Email, Id }
import usermanager.domain.user.read.{ UserRead, UserReadRepository }
import usermanager.infrastructure.jdbc.scalikejdbc.transaction.ScalikeJDBCTransaction

import scalaz.\/

class UserReadRepositoryScalikeJDBC extends UserReadRepository with RichUserScalikeJDBC {

  override def find(userId: Id[UserRead]): ScalikeJDBCTransaction[Option[UserRead]] = {
    def run(dbSession: DBSession) = \/.right(Users.find(userId)(dbSession).map(_.toDomain))
    ScalikeJDBCTransaction(run)
  }

  override def findByEmail(email: Email[UserRead]): ScalikeJDBCTransaction[Option[UserRead]] = {
    def run(dBSession: DBSession) = \/.right(Users.findBy(sqls"where email = $email")(dBSession).map(_.toDomain))
    ScalikeJDBCTransaction(run)
  }

}
