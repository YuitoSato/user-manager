package usermanager.infrastructure.rdb.scalikejdbc.user

import org.scalatest.{ MustMatchers, fixture }
import scalikejdbc.config.DBs
import scalikejdbc.scalatest.AutoRollback
import usermanager.infrastructure.rdb.scalikejdbc.transaction.{ ScalikeJDBCTransaction, ScalikeJDBCTransactionRunner }
import play.api.libs.concurrent.Execution.Implicits._
import scalikejdbc.DB
import usermanager.domain.aggregates.user.MockUser
import usermanager.domain.helpers.UUIDGenerator

class ScalikeJDBCTransactionRunnerSpec extends fixture.FunSpec
  with MustMatchers
  with AutoRollback {

  DBs.setup()

  implicit val runner: ScalikeJDBCTransactionRunner = new ScalikeJDBCTransactionRunner()
  val userRepository: UserRepositoryScalikeJDBC = new UserRepositoryScalikeJDBC()
  val uuid = new UUIDGenerator

  describe("run") {
    it("adds no user and rollbacks if transaction fails") { implicit session =>
      val before = Users.countAll()
      val wrongName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"

      val transaction = for {
        _ <- userRepository.create(MockUser(
          id = uuid.createId,
          email = uuid.createId)
        )
        // カラムが許容できない文字列をインサートさせ失敗させる。
        _ <- userRepository.create(MockUser(
          id = uuid.createId,
          userName = wrongName,
          email = uuid.createId)
        )
        _ <- userRepository.create(MockUser(
          id = uuid.createId,
          email = uuid.createId)
        )
      } yield()

      DB localTx { session =>
        transaction.asInstanceOf[ScalikeJDBCTransaction[Unit]].execute(session)
      }

      val after = Users.countAll()

      after mustBe before
    }
  }

}
