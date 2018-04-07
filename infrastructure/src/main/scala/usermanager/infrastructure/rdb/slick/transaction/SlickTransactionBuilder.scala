package usermanager.infrastructure.rdb.slick.transaction

import javax.inject.Inject

import slick.dbio.DBIO
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.async.AsyncTransactionBuilder

import scala.concurrent.ExecutionContext
import scalaz.{ \/, \/- }

class SlickTransactionBuilder @Inject()(
  implicit ec: ExecutionContext
) extends AsyncTransactionBuilder with ToEitherOps {

  override def exec[A](value: A): SlickTransaction[A] = {
    val dbio: DBIO[DomainError \/ A] = DBIO.successful(\/-(value))
    SlickTransaction(dbio.et)
  }

  override def exec[A](value: \/[DomainError, A]): SlickTransaction[A] = {
    val dbio: DBIO[DomainError \/ A] = DBIO.successful(value)
    SlickTransaction(dbio.et)
  }

}
