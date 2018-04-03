package usermanager.domain.result

trait Result[+A] {

  def map[B](f: A => B): Result[B]

  def flatMap[B](f: A => Result[B]): Result[B]

}
