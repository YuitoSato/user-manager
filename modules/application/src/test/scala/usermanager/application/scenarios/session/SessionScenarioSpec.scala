package usermanager.application.scenarios.session

import org.scalatest.{ FunSpec, MustMatchers }
import usermanager.application.error.ApplicationError
import usermanager.domain.aggregates.session.MockSessionUser
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.lib.result.MockResult

class SessionScenarioSpec extends FunSpec with MustMatchers {

  val scenario = new MockSessionScenario

  describe("findById") {
    describe("when session exists") {
      it("returns session user") {
        val success = MockResult(MockSessionUser())
        val result = scenario.findById("1")

        result mustBe success
      }
    }

    describe("when session does not exist") {
      it("returns not found error") {
        val failure = MockResult.error(ApplicationError.NotFound(SessionUser.TYPE, "NotFoundId"))
        val result = scenario.findById("NotFoundId")

        result mustBe failure
      }
    }
  }

  describe("create") {
    it("returns unit") {
      scenario.create(MockSessionUser()) mustBe MockResult(())
    }
  }

  describe("delete") {
    describe("when session exists") {
      it("returns success deleted result") {
        val success = MockResult(())
        scenario.delete("1") mustBe success
      }
    }
    describe("when session does not exist") {
      it("returns failure deleted result") {
        val failure = MockResult.error(ApplicationError.NotFound(SessionUser.TYPE, "NotFoundId"))
        scenario.delete("NotFoundId") mustBe failure
      }
    }
  }

}
