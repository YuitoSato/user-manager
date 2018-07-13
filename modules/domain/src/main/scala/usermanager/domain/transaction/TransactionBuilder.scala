package usermanager.domain.transaction

import usermanager.domain.error.Error
import scalaz.\/

trait TransactionBuilder {

  def build[A](value: \/[Error, A]): Transaction[A]

  def build[A](value: A): Transaction[A]

  def sequence[A](seq: Seq[Transaction[A]]): Transaction[Seq[A]] = {
    seq.toList.foldLeft(build(Nil: List[A])) {
      (resultSeq, resultA) => resultSeq.zipWith(resultA)((l, a) => a :: l)
    }.map(_.reverse.toSeq)
  }

}
