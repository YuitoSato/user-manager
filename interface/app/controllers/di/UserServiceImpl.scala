package controllers.di

import javax.inject.{ Inject, Named, Singleton }

import usermanager.application.services.user.UserService
import usermanager.domain.aggregates.user.UserRepository
import usermanager.domain.transaction.TransactionBuilder

@Singleton
class UserServiceImpl @Inject()(
  @Named(RDB.Scalikejdbc) val userRepository: UserRepository,
  @Named(RDB.Scalikejdbc) implicit val transactionBuilder: TransactionBuilder
) extends UserService
