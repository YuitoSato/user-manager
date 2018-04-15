package usermanager.application.services.user

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{ FunSpec, MustMatchers }
import usermanager.domain.aggregates.user.read.{ MockUserRead, MockUserReadRepository, UserRead }
import usermanager.domain.aggregates.user.write.UserWriteRepository
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.{ MockTransaction, MockTransactionBuilder }

import scalaz.{ -\/, \/- }

class UserServiceSpec extends FunSpec with MustMatchers with MockitoSugar {

  val readRepository = new MockUserReadRepository

  val writeRepository: UserWriteRepository = mock[UserWriteRepository]

  val builder = new MockTransactionBuilder

  val service: UserService = new UserService {
    val userReadRepository: MockUserReadRepository = readRepository
    val userWriteRepository: UserWriteRepository = writeRepository
    implicit val transactionBuilder: MockTransactionBuilder = builder
  }

  describe("findById") {
    describe("when user exists") {
      it("asserts that userName is 'Hoge'") {
        val success = MockTransaction(\/-(MockUserRead()))
        val transaction = service.findById("1")
        transaction mustBe success
      }
    }

    describe("when user does not exist") {
      it("returns NotFound error") {
        val failure = MockTransaction(-\/(DomainError.NotFound(UserRead.ID, "NotFoundId")))
        val transaction = service.findById("NotFoundId")
        transaction mustBe failure
      }
    }
  }
}
