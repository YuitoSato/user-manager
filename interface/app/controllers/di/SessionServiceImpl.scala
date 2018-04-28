package controllers.di

import javax.inject.{ Inject, Named }

import usermanager.application.services.session.SessionService
import usermanager.domain.aggregates.sessionuser.SessionUserRepository
import usermanager.domain.transaction.TransactionBuilder

import scala.concurrent.ExecutionContext

class SessionServiceImpl @Inject()(
  @Named(Cache.Shade) val sessionRepository: SessionUserRepository,
  @Named(Cache.Shade) implicit val transactionBuilder: TransactionBuilder
) extends SessionService
