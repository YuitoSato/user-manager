package usermanager.infrastructure.rdb.scalikejdbc.user

import java.time.LocalDateTime
import javax.inject.Inject

import scalikejdbc._
import usermanager.domain.aggregates.user.{ User, UserRepository }
import usermanager.domain.types.{ Email, Id }
import usermanager.infrastructure.rdb.scalikejdbc.transaction.ScalikeJDBCTransaction

class UserRepositoryScalikeJDBC @Inject()
  extends UserRepository with RichUserScalikeJDBC {

  override def find(userId: Id[User]): ScalikeJDBCTransaction[Option[User]] = {
    ScalikeJDBCTransaction.from { session: DBSession =>
      Users.find(userId)(session).map(_.toEntity)
    }
  }

  override def findByEmail(email: Email[User]): ScalikeJDBCTransaction[Option[User]] = {
    ScalikeJDBCTransaction.from { session: DBSession =>
      Users.findBy(sqls.eq(Users.syntax("u").email, email.value))(session).map(_.toEntity)
    }
  }

  override def create(user: User): ScalikeJDBCTransaction[Unit] = {
    ScalikeJDBCTransaction.from { session: DBSession =>
      Users.create(
        userId = user.id,
        userName = user.userName,
        email = user.email,
        password = user.password,
        status = user.status,
        createdAt = LocalDateTime.now,
        updatedAt = LocalDateTime.now,
        versionNo = user.versionNo
      )(session)
    }.map(_ => ())
  }

}
