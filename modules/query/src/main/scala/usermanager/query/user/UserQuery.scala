package usermanager.query.user

import usermanager.lib.result.Result
import usermanager.lib.transaction.Transaction
import usermanager.query.base.QueryBase
import usermanager.query.error.QueryError
import usermanager.query.types.{ Email, Id }

trait UserQuery extends QueryBase {

  def findById(userId: Id[UserView]): Result[UserView] = {
    val transaction = _findById(userId) assertExists QueryError.NotFound(UserView.TYPE, userId)
    transaction.run
  }

  protected def _findById(userId: Id[UserView]): Transaction[Option[UserView]]

  def findByEmail(email: Email[UserView]): Result[UserView] = {
    val transaction = _findByEmail(email) assertExists QueryError.EmailNotFound
    transaction.run
  }

  protected def _findByEmail(email: Email[UserView]): Transaction[Option[UserView]]

}
