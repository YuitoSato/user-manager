package usermanager.domain.transaction

case class MockTransaction[A](
  value: A
) extends Transaction[A] {

  override def map[B](f: A => B): Transaction[B] = MockTransaction(f(value))

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] =
    MockTransaction(flatMap(MockTransaction(_)).asInstanceOf[MockTransaction[B]].value)
}
