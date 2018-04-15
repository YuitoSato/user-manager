package usermanager.application.services.user

import org.scalatest.{ FunSpec, MustMatchers }
import usermanager.domain.aggregates.user.read.{ MockUserRead, UserRead }
import usermanager.domain.aggregates.user.write.MockUserWrite
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.MockTransaction

import scalaz.{ -\/, \/- }

class UserServiceSpec extends FunSpec with MustMatchers {

  val service: UserService = new MockUserService

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
        val failure = MockTransaction(-\/(DomainError.NotFound(UserRead.TYPE, "NotFoundId")))
        val transaction = service.findById("NotFoundId")
        transaction mustBe failure
      }
    }
  }

  describe("create") {
    it("returns unit transaction") {
      val success = MockTransaction(\/-(()))
      val transaction = service.create(MockUserWrite())
      transaction mustBe success
    }
  }
}
