package usermanager.lib.transaction

import scalaz.\/
import usermanager.lib.error.Error

trait TransactionBuilder {

  def build[A](value: \/[Error, A]): Transaction[A]

  def build[A](value: A): Transaction[A]

  def sequence[A](seq: Seq[Transaction[A]]): Transaction[Seq[A]] = {
    seq.toList.foldLeft(build(Nil: List[A])) {
      (resultSeq, resultA) => resultSeq.zipWith(resultA)((l, a) => a :: l)
    }.map(_.reverse.toSeq)
  }

}
