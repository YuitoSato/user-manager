package usermanager.infrastructure.rdb.slick.user

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.MySQLProfile.api._
import usermanager.domain.syntax.ToEitherOps
import usermanager.infrastructure.rdb.slick.Tables.Users
import usermanager.infrastructure.rdb.slick.transaction.SlickTransaction
import usermanager.lib.transaction.{ Transaction, TransactionBuilder }
import usermanager.query.types.{ Email, Id }
import usermanager.query.user.{ UserQuery, UserView }

import scala.concurrent.ExecutionContext

class UserQuerySlick @Inject()(
  implicit val ec: ExecutionContext,
  implicit val dbConfigProvider: DatabaseConfigProvider,
  implicit val transactionBuilder: TransactionBuilder
) extends UserQuery with RichUserSlick with ToEitherOps {

  override protected def _findById(userId: Id[UserView]): Transaction[Option[UserView]] = {
    SlickTransaction.from { () =>
      Users
        .filter(_.userId === userId.value.bind)
        .result
        .headOption
        .map(_.map(_.toView))
    }
  }

  override protected def _findByEmail(email: Email[UserView]): Transaction[Option[UserView]] = {
    SlickTransaction.from { () =>
      Users
        .filter(_.email === email.value.bind)
        .result
        .headOption
        .map(_.map(_.toView))
    }
  }

}
