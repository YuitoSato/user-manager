package usermanager.application.scenarios.user

import usermanager.application.services.user.UserService
import usermanager.domain.aggregates.user.read.UserRead
import usermanager.domain.aggregates.user.write.UserWrite
import usermanager.domain.result.Result
import usermanager.domain.transaction.TransactionRunner
import usermanager.domain.types.{ Email, Id }

trait UserScenario {

  val userService: UserService
  implicit val transactionRunner: TransactionRunner

  def findById(userId: Id[UserRead]): Result[UserRead] = {
    userService.findById(userId).run
  }

  def findByEmail(email: Email[UserRead]): Result[UserRead] = {
    userService.findByEmail(email).run
  }

  def create(user: UserWrite): Result[Unit] = {
    userService.create(user).run
  }

}
