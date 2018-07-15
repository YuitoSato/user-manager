package usermanager.application.scenarios.user

import org.scalatest.{ FunSpec, MustMatchers }
import usermanager.application.error.ApplicationError
import usermanager.domain.aggregates.user.{ MockUser, User }
import usermanager.lib.result.SyncResult

class UserScenarioSpec extends FunSpec with MustMatchers {

  val scenario = new MockUserScenario

  describe("findById") {
    describe("when user exists") {
      it("asserts that userName is 'Hoge'") {
        val success = SyncResult(MockUser())
        val result = scenario.findById("1")
        result mustBe success
      }
    }

    describe("when user does not exist") {
      it("returns NotFound error") {
        val failure = SyncResult.error(ApplicationError.NotFound(User.TYPE, "NotFoundId"))
        val result = scenario.findById("NotFoundId")
        result mustBe failure
      }
    }

    describe("create") {
      describe("sucess") {
        it("returns unit result") {
          val success = SyncResult(())
          val result = scenario.create(MockUser(email = "notfound@example.com"))
          result mustBe success
        }
      }

      describe("when email already exists") {
        it("returns EmailExists error") {
          val failure = SyncResult.error(ApplicationError.EmailExists)
          val result = scenario.create(MockUser())
          result mustBe failure
        }
      }
    }
  }

}