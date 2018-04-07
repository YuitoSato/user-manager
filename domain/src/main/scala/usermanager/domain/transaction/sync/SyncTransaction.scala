package usermanager.domain.transaction.sync

import usermanager.domain.result.sync.SyncResult
import usermanager.domain.transaction.Transaction

trait SyncTransaction[A] extends Transaction[A] { self =>

  def map[B](f: A => B): SyncTransaction[B]

  def flatMap[B](f: A => SyncTransaction[B]): SyncTransaction[B]

  def run(implicit syncTransactionRunner: SyncTransactionRunner): SyncResult[A] = syncTransactionRunner.exec(self)

}
