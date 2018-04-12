package usermanager.infrastructure.rdb.scalikejdbc.user

import javax.inject.Inject

import scalikejdbc._
import usermanager.domain.aggregates.user.read.{ UserRead, UserReadRepository }
import usermanager.domain.types.{ Email, Id }
import usermanager.infrastructure.rdb.scalikejdbc.transaction.ScalikeJDBCTransaction

class UserReadRepositoryScalikeJDBC @Inject()
  extends UserReadRepository with RichUserScalikeJDBC {

  override def find(userId: Id[UserRead]): ScalikeJDBCTransaction[Option[UserRead]] = {
    ScalikeJDBCTransaction.from { session: DBSession =>
      Users.find(userId)(session).map(_.toEntity)
    }
  }

  override def findByEmail(email: Email[UserRead]): ScalikeJDBCTransaction[Option[UserRead]] = {
    ScalikeJDBCTransaction.from { session: DBSession =>
      Users.findBy(sqls.eq(Users.syntax("u").email, email.value))(session).map(_.toEntity)
    }
  }

}
