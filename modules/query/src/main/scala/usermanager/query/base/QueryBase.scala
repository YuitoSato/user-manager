package usermanager.query.base

import usermanager.lib.error.ErrorHandler
import usermanager.lib.transaction.TransactionBuilder

trait QueryBase extends ErrorHandler {

  implicit val transactionBuilder: TransactionBuilder

}
