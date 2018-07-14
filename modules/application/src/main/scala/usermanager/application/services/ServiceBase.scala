package usermanager.application.services

import usermanager.lib.error.ErrorHandler
import usermanager.lib.error.transaction.TransactionBuilder

trait ServiceBase extends ErrorHandler {

  implicit val transactionBuilder: TransactionBuilder

}
