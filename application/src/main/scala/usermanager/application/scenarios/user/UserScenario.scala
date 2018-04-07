package usermanager.application.scenarios.user

import com.google.inject.name.Named
import com.google.inject.{ Inject, Singleton }
import usermanager.application.services.user.UserService
import usermanager.domain.result.sync.SyncResult
import usermanager.domain.transaction.sync.SyncTransactionRunner
import usermanager.domain.aggregates.user.read.UserRead

import scala.concurrent.ExecutionContext

@Singleton
class UserScenario @Inject()(
  userService: UserService,
  @Named("rdb.scalikejdbc") implicit val syncTransactionRunner: SyncTransactionRunner
)(
  implicit ec: ExecutionContext,
) {

  def findByEmail(email: String): SyncResult[UserRead] = {
    userService.findByEmail(email).run
  }

}
