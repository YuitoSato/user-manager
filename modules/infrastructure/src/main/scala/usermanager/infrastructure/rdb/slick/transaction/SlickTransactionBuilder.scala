package usermanager.infrastructure.rdb.slick.transaction

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.DBIO
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.ExecutionContext
import scalaz.{ \/, \/- }
import usermanager.lib.error
import usermanager.lib.error.Error
import usermanager.lib.error.transaction.TransactionBuilder

class SlickTransactionBuilder @Inject()(
  implicit ec: ExecutionContext,
  implicit val dbConfigProvider: DatabaseConfigProvider
) extends TransactionBuilder with ToEitherOps {

  override def build[A](value: A): SlickTransaction[A] = {
    val dbio: DBIO[error.Error \/ A] = DBIO.successful(\/-(value))
    val exec = () => dbio.et
    SlickTransaction(exec)
  }

  override def build[A](value: \/[error.Error, A]): SlickTransaction[A] = {
    val dbio: DBIO[Error \/ A] = DBIO.successful(value)
    val exec = () => dbio.et
    SlickTransaction(exec)
  }

}
