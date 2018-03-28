package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.transaction.{ ReadTransaction, ReadWriteTransaction }


abstract class ScalikeJDBCTransaction(val session: DBSession)

class ScalikeJDBCReadTransaction(session: DBSession) extends ScalikeJDBCTransaction(session) with ReadTransaction

class ScalikeJDBCReadWriteTransaction(session: DBSession) extends ScalikeJDBCTransaction(session) with ReadWriteTransaction
