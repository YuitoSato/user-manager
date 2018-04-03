package usermanager.domain.transaction

trait Transaction[+A] {

  def map[B](f: A => B): Transaction[B]

  def flatMap[B](f: A => Transaction[B]): Transaction[B]

}
