package usermanager.domain.aggregates.user.read
import usermanager.domain.transaction.{ MockTransaction, Transaction }
import usermanager.domain.types.{ Email, Id }

import scalaz.\/-

class MockUserReadRepository extends UserReadRepository {

  override def find(userId: Id[UserRead]): Transaction[Option[UserRead]] = {
    userId match {
      case Id("NotFoundId") => MockTransaction(\/-(None))
      case _ => MockTransaction(\/-(Some(MockUserRead())))
    }

  }

  override def findByEmail(email: Email[UserRead]): Transaction[Option[UserRead]] = {
    email match {
      case Email("notfound@example.com") => MockTransaction(\/-(None))
      case _ => MockTransaction(\/-(Some(MockUserRead())))
    }
  }
}
