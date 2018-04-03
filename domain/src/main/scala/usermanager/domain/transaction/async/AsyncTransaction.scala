package usermanager.domain.transaction.async

import usermanager.domain.transaction.Transaction

trait AsyncTransaction[+A] extends Transaction[A] {

  def map[B](f: A => B): AsyncTransaction[B]

  def flatMap[B](f: A => AsyncTransaction[B]): AsyncTransaction[B]

}
