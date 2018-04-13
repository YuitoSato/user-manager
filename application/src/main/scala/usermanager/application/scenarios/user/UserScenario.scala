package usermanager.application.scenarios.user

import javax.inject.Singleton

import com.google.inject.Inject
import com.google.inject.name.Named
import usermanager.application.services.user.UserService
import usermanager.domain.aggregates.user.read.UserRead
import usermanager.domain.aggregates.user.write.UserWrite
import usermanager.domain.result.Result
import usermanager.domain.transaction.TransactionRunner
import usermanager.domain.types.{ Email, Id }

import scala.concurrent.ExecutionContext

@Singleton
class UserScenario @Inject()(
  userService: UserService,
  @Named("rdb.slick") implicit val transactionRunner: TransactionRunner
)(
  implicit ec: ExecutionContext,
) {

  def findById(userId: Id[UserRead]): Result[UserRead] = {
    userService.findById(userId).run
  }

  def findByEmail(email: Email[UserRead]): Result[UserRead] = {
    userService.findByEmail(email).run
  }

  def create(user: UserWrite): Result[Unit] = {
    val error = user.copy(id = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
    (for {
      _ <- userService.create(user.copy(id = "111"))
      _ <- userService.create(user.copy(id = "221"))
      _ <- userService.create(error)
      _ <- userService.create(user.copy(id = "444"))
    } yield ()).run
  }

//  def update(user: UserWrite): SyncResult[Unit] = {
//    userService.
//  }

}
