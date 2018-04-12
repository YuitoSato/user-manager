package usermanager.infrastructure.rdb.slick.user

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.inject.Inject

import slick.jdbc.MySQLProfile.api._
import usermanager.domain.aggregates.user.write.{ UserWrite, UserWriteRepository }
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction
import usermanager.infrastructure.rdb.slick.Tables._
import usermanager.infrastructure.rdb.slick.transaction.SlickTransaction

import scala.concurrent.ExecutionContext
import scalaz.{ \/, \/- }

class UserWriteRepositorySlick @Inject()(
  implicit val ec: ExecutionContext
) extends UserWriteRepository with RichUserSlick with ToEitherOps {

  override def create(user: UserWrite): Transaction[Unit] = {
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
