package controllers.session

import controllers.di.Cache
import javax.inject.{ Inject, Named }
import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.services.session.SessionService
import usermanager.domain.aggregates.sessionuser.SessionRepository
import usermanager.domain.transaction.TransactionBuilder

class SessionScenarioImpl @Inject()(
  @Named(Cache.Shade) val sessionRepositoryImpl: SessionRepository,
  @Named(Cache.Shade) implicit val transactionBuilderImpl: TransactionBuilder,
) extends SessionScenario {

  override val sessionService: SessionService = new SessionService {
    override val sessionRepository: SessionRepository = sessionRepositoryImpl
    override implicit val transactionBuilder: TransactionBuilder = transactionBuilderImpl
  }

}
