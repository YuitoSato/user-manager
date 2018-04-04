package usermanager.infrastructure.jdbc.slick.transaction

import javax.inject.Inject

import repositories.transaction.{ Transaction, TransactionBuilder }
import slick.dbio.DBIO

import scala.concurrent.ExecutionContext

class SlickTransactionBuilder @Inject()(
  implicit ec: ExecutionContext
) extends TransactionBuilder {

  override def exec[A](value: A): SlickTransaction[A] = {
    SlickTransaction(DBIO.successful(value))
  }

  override def sequence[A](list: List[Transaction[A]]): Transaction[List[A]] = {
    SlickTransaction(DBIO.sequence(list.map(t => t.asInstanceOf[SlickTransaction[A]].value)))
  }
}
