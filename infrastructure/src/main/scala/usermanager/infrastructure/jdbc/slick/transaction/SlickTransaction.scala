package usermanager.infrastructure.jdbc.slick.transaction

import slick.dbio.DBIO
import usermanager.domain.transaction.Transaction

import scala.concurrent.ExecutionContext

abstract class SlickTransaction[A](
  val dbio: A => DBIO[A]
)

class SlickReadTransaction[A](dbio: A => DBIO[A]) extends SlickTransaction[A](dbio)

class SlickReadWriteTransaction[A](dbio: A => DBIO[A])
  extends SlickTransaction[A](dbio)
