package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.transaction._

import scala.concurrent.{ ExecutionContext, Future }

trait ScalikeJDBCTaskBuilder {

  def ask: Task[Transaction, DBSession] =
    new Task[Transaction, DBSession] {
      def execute(transaction: Transaction)(implicit ec: ExecutionContext): Future[DBSession] =
        Future.successful(transaction.asInstanceOf[ScalikeJDBCTransaction].session)
    }

}
