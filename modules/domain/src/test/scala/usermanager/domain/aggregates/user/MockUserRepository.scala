package usermanager.domain.aggregates.user

import usermanager.domain.transaction.MockTransaction
import usermanager.domain.types.{ Email, Id }
import scalaz.\/-
import usermanager.lib.error.transaction.Transaction

class MockUserRepository extends UserRepository {

  override def find(userId: Id[User]): Transaction[Option[User]] = {
    MockTransaction { () =>
      userId match {
        case Id("NotFoundId") => \/-(None)
        case _ => \/-(Some(MockUser()))
      }
    }
  }

  override def findByEmail(email: Email[User]): Transaction[Option[User]] = {
    MockTransaction { () =>
      email match {
        case Email("notfound@example.com") => \/-(None)
        case _ => \/-(Some(MockUser()))
      }
    }
  }

  override def create(user: User): Transaction[Unit] = {
    MockTransaction.from(() => ())
  }

}
