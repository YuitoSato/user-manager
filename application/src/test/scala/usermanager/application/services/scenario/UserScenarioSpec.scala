package usermanager.application.services.scenario

import org.scalatest.{ FunSpec, MustMatchers }
import usermanager.domain.aggregates.user.{ MockUser, User }
import usermanager.domain.error.DomainError
import usermanager.domain.result.MockResult

import scalaz.{ -\/, \/- }

class UserScenarioSpec extends FunSpec with MustMatchers {

  val scenario = new MockUserScenario

  describe("findById") {
    describe("when user exists") {
      it("asserts that userName is 'Hoge'") {
        val success = MockResult(\/-(MockUser()))
        val result = scenario.findById("1")
        result mustBe success
      }
    }

    describe("when user does not exist") {
      it("returns NotFound error") {
        val failure = MockResult(-\/(DomainError.NotFound(User.TYPE, "NotFoundId")))
        val result = scenario.findById("NotFoundId")
        result mustBe failure
      }
    }

    describe("create") {
      describe("sucess") {
        it("returns unit result") {
          val success = MockResult(\/-(()))
          val result = scenario.create(MockUser(email = "notfound@example.com"))
          result mustBe success
        }
      }

      describe("when email already exists") {
        it("returns EmailExists error") {
          val failure = MockResult(-\/(DomainError.EmailExists("hoge@example.com")))
          val result = scenario.create(MockUser())
          result mustBe failure
        }
      }
    }
  }

}
