package usermanager.domain.aggregates.user

import usermanager.domain.aggregates.user.{ User, UserRepository }
import usermanager.domain.transaction.{ MockTransaction, Transaction }
import usermanager.domain.types.{ Email, Id }

import scalaz.\/-

class MockUserRepository extends UserRepository {

  override def find(userId: Id[User]): Transaction[Option[User]] = {
    userId match {
      case Id("NotFoundId") => MockTransaction(\/-(None))
      case _ => MockTransaction(\/-(Some(MockUser())))
    }

  }

  override def findByEmail(email: Email[User]): Transaction[Option[User]] = {
    email match {
      case Email("notfound@example.com") => MockTransaction(\/-(None))
      case _ => MockTransaction(\/-(Some(MockUser())))
    }
  }

  override def create(user: User): Transaction[Unit] = {
    MockTransaction(\/-(()))
  }

}
