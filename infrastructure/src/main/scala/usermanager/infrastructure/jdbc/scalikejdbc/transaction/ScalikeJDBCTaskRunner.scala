package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.DB
import usermanager.domain.transaction.{ ReadTransaction, ReadWriteTransaction, Task, TaskRunner }

import scala.concurrent.Future

trait ScalikeJDBCTaskRunner {

  implicit def readRunner[R >: ReadTransaction]: TaskRunner[R] =
    new TaskRunner[R] {
      def run[A](task: Task[R, A]): Future[A] = {
        val session = DB.readOnlySession()
        val future = task.execute(new ScalikeJDBCReadTransaction(session))
        future.onComplete(_ => session.close())
        future
      }
    }

  implicit def readWriteRunner[R >: ReadWriteTransaction]: TaskRunner[R] =
    new TaskRunner[R] {
      def run[A](task: Task[R, A]): Future[A] = {
        DB.futureLocalTx(session => task.execute(new ScalikeJDBCReadWriteTransaction(session)))
      }
    }

}
