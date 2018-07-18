package usermanager.application.services.user

import org.scalatest.{ FunSpec, Matchers }
import org.typelevel.scalatest.DisjunctionMatchers
import usermanager.application.error.ApplicationError
import usermanager.domain.aggregates.user.{ MockUser, User }
import usermanager.lib.result.MockTransactionOps
import usermanager.lib.error._

class UserServiceSpec extends FunSpec with MockTransactionOps with Matchers with DisjunctionMatchers {

  val service = new MockUserService

  describe("#findById") {
    describe("when user exists") {
      it("should return mock user.") {
        val result = service.findById("1").value
        result should beRight(MockUser())
      }
    }

    describe("when user does not exist") {
      it("should return NotFound error") {
        val result = service.findById("NotFoundId").value
        val error: Error = ApplicationError.NotFound(User.TYPE, "NotFoundId")
        result should beLeft(error)
      }
    }
  }

}
