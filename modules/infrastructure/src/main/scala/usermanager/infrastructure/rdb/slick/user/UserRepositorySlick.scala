package usermanager.infrastructure.rdb.slick.user

import java.sql.Timestamp
import java.time.LocalDateTime

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.MySQLProfile.api._
import usermanager.domain.aggregates.user.{ User, UserRepository }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.types.{ Email, Id }
import usermanager.infrastructure.rdb.slick.Tables._
import usermanager.infrastructure.rdb.slick.transaction.SlickTransaction
import usermanager.lib.transaction.Transaction

import scala.concurrent.ExecutionContext

class UserRepositorySlick @Inject()(
  implicit val ec: ExecutionContext,
  implicit val dbConfigProvider: DatabaseConfigProvider
) extends UserRepository with RichUserSlick with ToEitherOps {

  override def find(userId: Id[User]): Transaction[Option[User]] = {
    SlickTransaction.from { () =>
      Users
        .filter(_.userId === userId.value.bind)
        .result
        .headOption
        .map(_.map(_.toEntity))
    }
  }

  override def findByEmail(email: Email[User]): Transaction[Option[User]] = {
    SlickTransaction.from { () =>
      Users
        .filter(_.email === email.value.bind)
        .result
        .headOption
        .map(_.map(_.toEntity))
    }
  }

  override def create(user: User): Transaction[Unit] = {
    SlickTransaction.from { () =>
      (Users += UsersRow(
        userId = user.id,
        userName = user.userName,
        email = user.email,
        password = user.password,
        status = user.status,
        versionNo = user.versionNo,
        createdAt = Timestamp.valueOf(LocalDateTime.now),
        updatedAt = Timestamp.valueOf(LocalDateTime.now)
      )).map(_ => ())
    }
  }
}
