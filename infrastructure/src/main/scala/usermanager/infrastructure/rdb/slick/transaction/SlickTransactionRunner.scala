package usermanager.infrastructure.rdb.slick.transaction

import javax.inject.Inject

import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.JdbcProfile
import usermanager.domain.result.async.AsyncResult
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionRunner }

import scala.concurrent.ExecutionContext

class SlickTransactionRunner @Inject()(
  val dbConfigProvider: DatabaseConfigProvider
)(
  implicit ec: ExecutionContext
) extends AsyncTransactionRunner
  with HasDatabaseConfigProvider[JdbcProfile]
  with ToEitherOps {

  override def execute[A](transaction: AsyncTransaction[A]): AsyncResult[A] = {
    AsyncResult(db.run(transaction.asInstanceOf[SlickTransaction[A]].value.run).et)
  }

}
