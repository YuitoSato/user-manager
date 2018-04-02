package usermanager.infrastructure.jdbc.slick.transaction

import usermanager.domain.transaction.{ Task, Transaction }

import scala.concurrent.{ ExecutionContext, Future }

trait SlickTaskBuilder {

  def ask: Task[Transaction, Unit] =
    new Task[Transaction, Unit] {
      override def execute(transaction: Transaction)(implicit ec: ExecutionContext): Future[Unit] =
        Future.successful(transaction.asInstanceOf[SlickTransaction].dbio)
    }

}
