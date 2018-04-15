package controllers.user

import javax.inject.{ Inject, Named, Singleton }

import usermanager.application.services.user.UserService
import usermanager.domain.aggregates.user.read.UserReadRepository
import usermanager.domain.aggregates.user.write.UserWriteRepository
import usermanager.domain.transaction.TransactionBuilder

import scala.concurrent.ExecutionContext

@Singleton
class UserServiceImpl @Inject()(
  @Named("rdb.slick") val userReadRepository: UserReadRepository,
  @Named("rdb.slick") val userWriteRepository: UserWriteRepository,
  @Named("rdb.slick") implicit val transactionBuilder: TransactionBuilder
)(
  implicit ec: ExecutionContext
) extends UserService
