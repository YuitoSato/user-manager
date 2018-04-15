package controllers.di

import javax.inject.{ Inject, Named, Singleton }

import usermanager.application.services.user.UserService
import usermanager.domain.aggregates.user.UserRepository
import usermanager.domain.transaction.TransactionBuilder

import scala.concurrent.ExecutionContext

@Singleton
class UserServiceImpl @Inject()(
  @Named(RDB.Scalikejdbc) val userRepository: UserRepository,
  @Named(RDB.Scalikejdbc) implicit val transactionBuilder: TransactionBuilder
)(
  implicit ec: ExecutionContext
) extends UserService
