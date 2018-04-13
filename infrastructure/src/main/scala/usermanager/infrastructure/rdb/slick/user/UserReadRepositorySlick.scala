package usermanager.infrastructure.rdb.slick.user

import javax.inject.Inject

import slick.jdbc.MySQLProfile.api._
import usermanager.domain.aggregates.user.read.{ UserRead, UserReadRepository }
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction
import usermanager.domain.types.{ Email, Id }
import usermanager.infrastructure.rdb.slick.Tables._
import usermanager.infrastructure.rdb.slick.transaction.SlickTransaction

import scala.concurrent.ExecutionContext
import scalaz.{ \/, \/- }

class UserReadRepositorySlick @Inject()(
  implicit val ec: ExecutionContext
) extends UserReadRepository with RichUserSlick with ToEitherOps {

  override def find(userId: Id[UserRead]): Transaction[Option[UserRead]] = {
    val either: DBIO[DomainError \/ Option[UserRead]] = Users
      .filter(_.userId === userId.value.bind)
      .result
      .headOption
      .map(opt => \/-(opt.map(_.toEntity))).transactionally

    val either2: DBIO[DomainError \/ Option[UserRead]] = Users
      .filter(_.userId === userId.value.bind)
      .result
      .headOption
      .map(opt => \/-(opt.map(_.toEntity)))

    either2

    either
    val a = Users.result.headOption
    SlickTransaction(either.et)


  }

  override def findByEmail(email: Email[UserRead]): Transaction[Option[UserRead]] = {
    val either: DBIO[DomainError \/ Option[UserRead]] = Users
      .filter(_.email === email.value.bind)
      .result
      .headOption
      .map(opt => \/-(opt.map(_.toEntity)))
    SlickTransaction(either.et)
  }
}
