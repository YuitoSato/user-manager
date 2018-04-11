package usermanager.application.scenarios.user

import com.google.inject.Inject
import com.google.inject.name.Named
import usermanager.application.services.user.UserService
import usermanager.domain.aggregates.user.read.UserRead
import usermanager.domain.aggregates.user.write.UserWrite
import usermanager.domain.result.sync.SyncResult
import usermanager.domain.transaction.sync.SyncTransactionRunner
import usermanager.domain.types.{ Email, Id }

import scala.concurrent.ExecutionContext

class UserScenario @Inject()(
  userService: UserService,
  @Named("rdb.scalikejdbc") implicit val syncTransactionRunner: SyncTransactionRunner
)(
  implicit ec: ExecutionContext,
) {

  def findById(userId: Id[UserRead]): SyncResult[UserRead] = {
    userService.findById(userId).run
  }

  def findByEmail(email: Email[UserRead]): SyncResult[UserRead] = {
    userService.findByEmail(email).run
  }

  def create(user: UserWrite): SyncResult[Unit] = {
    val error = user.copy(id = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
    (for {
      _ <- userService.create(user)
      _ <- userService.create(error)
      _ <- userService.create(user)
    } yield ()).run
  }

//  def update(user: UserWrite): SyncResult[Unit] = {
//    userService.
//  }

}
