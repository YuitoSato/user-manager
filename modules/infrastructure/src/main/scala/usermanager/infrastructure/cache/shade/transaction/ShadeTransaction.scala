package usermanager.infrastructure.cache.shade.transaction

import usermanager.domain.result.{ AsyncResult, Result }
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success, Try }
import scalaz.{ -\/, EitherT, \/, \/- }
import scalaz.std.FutureInstances
import usermanager.lib.error
import usermanager.lib.error.Error
import usermanager.lib.error.transaction.Transaction

case class ShadeTransaction[A](
  execute: () => EitherT[Future, error.Error, A]
)(
  implicit ec: ExecutionContext
) extends Transaction[A] with FutureInstances { self =>

  override def map[B](f: A => B): Transaction[B] = {
    val exec = () => execute().map(f)
    ShadeTransaction(exec)
  }

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] = {
    val exec = () => execute().map(f).flatMap(_.asInstanceOf[ShadeTransaction[B]].execute())
    ShadeTransaction(exec)
  }

  override def foreach(f: A => Unit): Unit = map(f)

  override def run: Result[A] = AsyncResult(self.asInstanceOf[ShadeTransaction[A]].execute())

  override def leftMap(f: error.Error => error.Error): Transaction[A] = ShadeTransaction(() => execute().leftMap(f))

}

object ShadeTransaction extends ToEitherOps {

  def from[A](execute: () => Future[A])(implicit ec: ExecutionContext): ShadeTransaction[A] = {
    val exec = () => {
      val future: Future[error.Error \/ A] = Try {
        execute()
      } match {
        case Success(r) => r.map(\/-(_))
        case Failure(l) => Future.successful(-\/(Error.Unexpected(l)))
      }
      future.et
    }
    ShadeTransaction(exec)
  }

}
