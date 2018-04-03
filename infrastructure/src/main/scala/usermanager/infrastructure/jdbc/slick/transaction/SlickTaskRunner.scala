package usermanager.infrastructure.jdbc.slick.transaction

import slick.basic.DatabaseConfig
import slick.driver.JdbcProfile
import usermanager.domain.transaction.{ ReadTransaction, ReadWriteTransaction, Task, TaskRunner }

import scala.concurrent.Future

trait SlickTaskRunner {

  implicit def readRunner[R >: ReadTransaction](implicit db: JdbcProfile#Backend#Database): TaskRunner[R] =
    new TaskRunner[R] {
      def run[A](task: Task[R, A]): Future[A] = {
        task.execute()

      }
    }

  implicit def readWriteRunner[R >: ReadWriteTransaction]: TaskRunner[R] =
    new TaskRunner[R] {
      def run[A](task: Task[R, A]): Future[A] = ???
    }

}
