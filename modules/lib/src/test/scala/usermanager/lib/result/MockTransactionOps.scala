package usermanager.lib.result

import scalaz.\/
import usermanager.lib.error.Error
import usermanager.lib.transaction.Transaction

trait MockTransactionOps {

  implicit class RichMockTransaction[A](transaction: Transaction[A]) {
    def value: Error \/ A = transaction.run.asInstanceOf[MockResult[A]].value
  }

}
