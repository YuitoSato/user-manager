package usermanager.infrastructure.rdb.scalikejdbc.user

import java.time.LocalDateTime

import org.scalatest.{ MustMatchers, fixture }
import scalikejdbc.config._
import scalikejdbc.scalatest.AutoRollback
import usermanager.domain.aggregates.user.MockUser

class UserRepositoryScalikeJDBCSpec
  extends fixture.FunSpec
  with MustMatchers
  with AutoRollback {

  DBs.setup()

  describe("create") {
    it("create a user record") { implicit session =>
      val before = Users.countAll()
      val user = MockUser()
      Users.create(
        userId = user.id,
        userName = user.userName,
        email = user.email,
        password = user.password,
        status = user.status,
        versionNo = user.versionNo,
        createdAt = LocalDateTime.now,
        updatedAt = LocalDateTime.now
      )
      val after = Users.countAll()

      after mustBe before + 1
    }
  }

}
