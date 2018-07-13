package usermanager.infrastructure.rdb.slick.transaction

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.DBIO
import usermanager.domain.error.Error
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.TransactionBuilder

import scala.concurrent.ExecutionContext
import scalaz.{ \/, \/- }

class SlickTransactionBuilder @Inject()(
  implicit ec: ExecutionContext,
  implicit val dbConfigProvider: DatabaseConfigProvider
) extends TransactionBuilder with ToEitherOps {

  override def build[A](value: A): SlickTransaction[A] = {
    val dbio: DBIO[Error \/ A] = DBIO.successful(\/-(value))
    val exec = () => dbio.et
    SlickTransaction(exec)
  }

  override def build[A](value: \/[Error, A]): SlickTransaction[A] = {
    val dbio: DBIO[Error \/ A] = DBIO.successful(value)
    val exec = () => dbio.et
    SlickTransaction(exec)
  }

}
