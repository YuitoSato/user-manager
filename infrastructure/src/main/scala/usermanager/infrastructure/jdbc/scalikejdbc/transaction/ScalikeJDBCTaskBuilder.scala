package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.transaction._

import scala.concurrent.{ ExecutionContext, Future }

class ScalikeJDBCTaskBuilder()(
  implicit dBSession: DBSession
) extends TransactionBuilder {

  override def exec[A](value: A): Transaction[A] = {
    ScalikeJDBCTransaction(Future.successful(value))
  }

}
