package usermanager.domain.transaction.sync

import usermanager.domain.transaction.Transaction

trait SyncTransaction[+A] extends Transaction[A] {

  def map[B](f: A => B): SyncTransaction[B]

  def flatMap[B](f: A => SyncTransaction[B]): SyncTransaction[B]

}
