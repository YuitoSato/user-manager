package usermanager.application.services

import usermanager.domain.error.ErrorHandler
import usermanager.domain.transaction.TransactionBuilder

trait ServiceBase extends ErrorHandler {

  implicit val transactionBuilder: TransactionBuilder

}
