package usermanager.domain.transaction

import usermanager.domain.result.{ Result, SyncResult }
import scalaz.{ \/, \/- }
import usermanager.lib.error
import usermanager.lib.error.Error
import usermanager.lib.error.transaction.Transaction

case class MockTransaction[A](
  execute: () => error.Error \/ A
) extends Transaction[A] { self =>

  override def map[B](f: A => B): Transaction[B] = {
    val exec = () => execute().map(f)
    MockTransaction(exec)
  }

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] = {
    val exec = () => execute().map(f).flatMap(_.asInstanceOf[MockTransaction[B]].execute())
    MockTransaction(exec)
  }

  override def foreach(f: A => Unit): Unit = map(f)

  override def run: Result[A] = SyncResult {
    self.execute()
  }

  override def leftMap(f: error.Error => Error): Transaction[A] = MockTransaction(() => execute().leftMap(f))
  
}

object MockTransaction {

  def from[A](execute: () => A): MockTransaction[A] = {
    val exec = () => \/-(execute())
    MockTransaction(exec)
  }
}
