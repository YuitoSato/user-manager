package controllers.di

import javax.inject.{ Inject, Named, Singleton }

import di._
import usermanager.application.scenarios.user.UserScenario
import usermanager.application.services.user.UserService
import usermanager.domain.transaction.TransactionRunner

import scala.concurrent.ExecutionContext

@Singleton
class UserScenarioImpl @Inject()(
  val userService: UserService,
  @Named(RDB.Scalikejdbc) implicit val transactionRunner: TransactionRunner
)(
  implicit ec: ExecutionContext,
) extends UserScenario
