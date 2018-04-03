package usermanager.infrastructure.jdbc.slick.transaction

import slick.dbio.DBIO
import slick.jdbc.JdbcProfile
import usermanager.domain.transaction.{ ReadTransaction, ReadWriteTransaction }

abstract class SlickTransaction[A](
  val dbio: DBIO[A]
)(
  implicit val db: JdbcProfile#Backend#Database
)


class SlickReadTransaction[A](
  dbio: DBIO[A]
)(
  implicit override val db: JdbcProfile#Backend#Database
) extends SlickTransaction[A](dbio) with ReadTransaction


class SlickReadWriteTransaction[A](
  dbio: DBIO[A]
)(
  implicit override val db: JdbcProfile#Backend#Database
) extends SlickTransaction[A](dbio) with ReadWriteTransaction
