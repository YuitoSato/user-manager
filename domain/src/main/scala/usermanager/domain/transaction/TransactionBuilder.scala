package usermanager.domain.transaction

trait TransactionBuilder {

  def exec[A](value: A): Transaction[A]

}
