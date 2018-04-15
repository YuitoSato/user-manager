package controllers.di

import javax.inject.{ Inject, Named }

import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.services.session.SessionService
import usermanager.domain.transaction.TransactionRunner

import scala.concurrent.ExecutionContext

class SessionScenarioImpl @Inject()(
  val sessionService: SessionService,
  @Named(Cache.Shade) implicit val transactionRunner: TransactionRunner
)(
  implicit ec: ExecutionContext
) extends SessionScenario
