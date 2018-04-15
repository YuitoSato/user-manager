package usermanager.application.scenarios

import usermanager.domain.transaction.TransactionRunner

trait ScenarioBase {

  implicit val transactionRunner: TransactionRunner

}
