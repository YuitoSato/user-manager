package usermanager.infrastructure.cache.shade.transaction

import usermanager.domain.error.Error
import usermanager.domain.result.{ AsyncResult, Result }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success, Try }
import scalaz.{ -\/, EitherT, \/, \/- }
import scalaz.std.FutureInstances

case class ShadeTransaction[A](
  execute: () => EitherT[Future, Error, A]
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

  override def leftMap(f: Error => Error): Transaction[A] = ShadeTransaction(() => execute().leftMap(f))

}

object ShadeTransaction extends ToEitherOps {

  def from[A](execute: () => Future[A])(implicit ec: ExecutionContext): ShadeTransaction[A] = {
    val exec = () => {
      val future: Future[Error \/ A] = Try {
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
