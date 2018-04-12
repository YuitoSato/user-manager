package usermanager.infrastructure.rdb.slick.transaction

import javax.inject.Inject

import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.JdbcProfile
import usermanager.domain.result.AsyncResult
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.{ Transaction, TransactionRunner }

import scala.concurrent.ExecutionContext

class SlickTransactionRunner @Inject()(
  val dbConfigProvider: DatabaseConfigProvider
)(
  implicit ec: ExecutionContext
) extends TransactionRunner
  with HasDatabaseConfigProvider[JdbcProfile]
  with ToEitherOps {

  override def execute[A](transaction: Transaction[A]): AsyncResult[A] = {
    val dbio = transaction.asInstanceOf[SlickTransaction[A]].value.run
    AsyncResult(db.run(transaction.asInstanceOf[SlickTransaction[A]].value.run).et)
  }

}
