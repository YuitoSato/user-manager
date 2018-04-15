package usermanager.domain.aggregates.user.write

import usermanager.domain.transaction.{ MockTransaction, Transaction }

import scalaz.\/-

class MockUserWriteRepository extends UserWriteRepository {

  override def create(user: UserWrite): Transaction[Unit] = {
    MockTransaction(\/-(()))
  }

}
