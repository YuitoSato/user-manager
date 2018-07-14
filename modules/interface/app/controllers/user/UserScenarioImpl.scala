package controllers.user

import controllers.di.RDB
import javax.inject.{ Inject, Named, Singleton }
import usermanager.application.scenarios.user.UserScenario
import usermanager.application.services.user.UserService
import usermanager.domain.aggregates.user.UserRepository
import usermanager.lib.transaction.TransactionBuilder

@Singleton
class UserScenarioImpl @Inject()(
  @Named(RDB.Slick) val userRepositoryImpl: UserRepository,
  @Named(RDB.Slick) implicit val transactionBuilderImpl: TransactionBuilder,
) extends UserScenario {

  override val userService: UserService = new UserService {
    override val userRepository: UserRepository = userRepositoryImpl
    override implicit val transactionBuilder: TransactionBuilder = transactionBuilderImpl
  }

}
