package usermanager.infrastructure.rdb.slick.transaction

import javax.inject.Inject

import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import usermanager.domain.result.{ AsyncResult, Result }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.{ Transaction, TransactionRunner }

import scala.concurrent.ExecutionContext

class SlickTransactionRunner @Inject()(
  val dbConfigProvider: DatabaseConfigProvider
)(
  implicit ec: ExecutionContext
) extends TransactionRunner
  with HasDatabaseConfigProvider[JdbcProfile]
  with ToEitherOps
{

  override def execute[A](transaction: Transaction[A]): Result[A] = AsyncResult {
    val dbio = transaction.asInstanceOf[SlickTransaction[A]].execute().run
    db.run(dbio.transactionally).et
  }

}
