package usermanager.infrastructure.rdb.slick.transaction

import javax.inject.Inject

import slick.dbio.DBIO
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.TransactionBuilder

import scala.concurrent.ExecutionContext
import scalaz.{ \/, \/- }

class SlickTransactionBuilder @Inject()(
  implicit ec: ExecutionContext
) extends TransactionBuilder with ToEitherOps {

  override def execute[A](value: A): SlickTransaction[A] = {
    val dbio: DBIO[DomainError \/ A] = DBIO.successful(\/-(value))
    val exec = () => dbio.et
    SlickTransaction(exec)
  }

  override def execute[A](value: \/[DomainError, A]): SlickTransaction[A] = {
    val dbio: DBIO[DomainError \/ A] = DBIO.successful(value)
    val exec = () => dbio.et
    SlickTransaction(exec)
  }

}
