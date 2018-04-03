package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.transaction._
import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionBuilder }

import scala.concurrent.{ ExecutionContext, Future }

class ScalikeJDBCTransactionBuilder()(
  implicit dBSession: DBSession
) extends AsyncTransactionBuilder {

  override def exec[A](value: A): AsyncTransaction[A] = {
    ScalikeJDBCTransaction(Future.successful(value))
  }

}
