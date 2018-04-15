package controllers.user

import javax.inject.{ Inject, Named, Singleton }

import usermanager.application.services.user.UserService
import usermanager.domain.aggregates.user.read.UserReadRepository
import usermanager.domain.aggregates.user.write.UserWriteRepository
import usermanager.domain.transaction.TransactionBuilder

import scala.concurrent.ExecutionContext
import di._

@Singleton
class UserServiceImpl @Inject()(
  @Named(RDB.Scalikejdbc) val userReadRepository: UserReadRepository,
  @Named(RDB.Scalikejdbc) val userWriteRepository: UserWriteRepository,
  @Named(RDB.Scalikejdbc) implicit val transactionBuilder: TransactionBuilder
)(
  implicit ec: ExecutionContext
) extends UserService
