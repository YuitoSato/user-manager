package usermanager.infrastructure.rdb.slick.user

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.inject.Inject

import slick.jdbc.MySQLProfile.api._
import usermanager.domain.aggregates.user.{ User, UserRepository }
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction
import usermanager.domain.types.{ Email, Id }
import usermanager.infrastructure.rdb.slick.Tables._
import usermanager.infrastructure.rdb.slick.transaction.SlickTransaction

import scala.concurrent.ExecutionContext
import scalaz.{ \/, \/- }

class UserRepositorySlick @Inject()(
  implicit val ec: ExecutionContext
) extends UserRepository with RichUserSlick with ToEitherOps {

  override def find(userId: Id[User]): Transaction[Option[User]] = {
    val either: DBIO[DomainError \/ Option[User]] = Users
      .filter(_.userId === userId.value.bind)
      .result
      .headOption
      .map(opt => \/-(opt.map(_.toEntity))).transactionally
    SlickTransaction(either.et)
  }

  override def findByEmail(email: Email[User]): Transaction[Option[User]] = {
    val either: DBIO[DomainError \/ Option[User]] = Users
      .filter(_.email === email.value.bind)
      .result
      .headOption
      .map(opt => \/-(opt.map(_.toEntity)))
    SlickTransaction(either.et)
  }


  override def create(user: User): Transaction[Unit] = {
    val dbio: DBIO[DomainError \/ Unit]= (Users += UsersRow(
      userId = user.id,
      userName = user.userName,
      email = user.email,
      password = user.password,
      status = user.status,
      versionNo = user.versionNo,
      createdAt = Timestamp.valueOf(LocalDateTime.now),
      updatedAt = Timestamp.valueOf(LocalDateTime.now)
    )).map(_ => \/-(()))
    SlickTransaction(dbio.et)
  }
}
