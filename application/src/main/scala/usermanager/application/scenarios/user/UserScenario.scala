package usermanager.application.scenarios.user

import com.google.inject.Inject
import usermanager.application.services.user.UserService
import usermanager.domain.result.sync.SyncResult
import usermanager.domain.transaction.sync.SyncTransactionRunner
import usermanager.domain.aggregates.user.read.UserRead

import scala.concurrent.ExecutionContext

class UserScenario @Inject()(
  userService: UserService
)(
  implicit ec: ExecutionContext,
  implicit val syncTransactionRunner: SyncTransactionRunner
) {

  def findByEmail(email: String): SyncResult[UserRead] = {
    userService.findByEmail(email).run
  }

}
