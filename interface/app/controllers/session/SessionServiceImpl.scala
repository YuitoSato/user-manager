package controllers.session

import controllers.di.Cache
import javax.inject.{ Inject, Named }
import usermanager.application.services.session.SessionService
import usermanager.domain.aggregates.sessionuser.SessionUserRepository
import usermanager.domain.transaction.TransactionBuilder

class SessionServiceImpl @Inject()(
  @Named(Cache.Shade) val sessionRepository: SessionUserRepository,
  @Named(Cache.Shade) implicit val transactionBuilder: TransactionBuilder
) extends SessionService
