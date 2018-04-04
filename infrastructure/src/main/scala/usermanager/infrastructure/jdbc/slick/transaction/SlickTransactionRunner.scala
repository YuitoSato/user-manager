package usermanager.infrastructure.jdbc.slick.transaction

import javax.inject.Inject

import models.domain.Errors
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import repositories.transaction.TransactionRunner
import slick.driver.JdbcProfile
import syntax.{ DBResult, Result, ToEitherOps }

import scala.concurrent.ExecutionContext
import scalaz.{ Cont, \/ }

class SlickTransactionRunner @Inject()(
  val dbConfigProvider: DatabaseConfigProvider
)(
  implicit ec: ExecutionContext
) extends TransactionRunner with HasDatabaseConfigProvider[JdbcProfile] with ToEitherOps {

  override def exec[A](result: DBResult[A]): Result[A] = {
    val dbio = result.run.asInstanceOf[SlickTransaction[Errors \/ A]].value
    val a = db
    db.run(dbio).et
  }

  Cont
}
