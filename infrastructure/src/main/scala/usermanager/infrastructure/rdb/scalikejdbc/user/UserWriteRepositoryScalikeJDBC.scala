package usermanager.infrastructure.rdb.scalikejdbc.user

import java.time.LocalDateTime
import javax.inject.Inject

import scalikejdbc.DBSession
import usermanager.domain.aggregates.user.write.{ UserWrite, UserWriteRepository }
import usermanager.infrastructure.rdb.scalikejdbc.transaction.ScalikeJDBCTransaction

import scalaz.\/-

class UserWriteRepositoryScalikeJDBC @Inject() extends UserWriteRepository
  with RichUserScalikeJDBC {

  override def create(user: UserWrite): ScalikeJDBCTransaction[Unit] = {
    def run(dbSession: DBSession) = \/-(
      Users.create(
        userId = user.id,
        userName = user.userName,
        email = user.email,
        password = user.password,
        status = user.status,
        createdAt = LocalDateTime.now,
        updatedAt = LocalDateTime.now,
        versionNo = user.versionNo
      )
    )
    ScalikeJDBCTransaction(run).map(_ => ())
  }

//  override def update(user: UserWrite) = ???
}
