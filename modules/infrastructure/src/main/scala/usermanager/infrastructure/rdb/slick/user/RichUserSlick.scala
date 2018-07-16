package usermanager.infrastructure.rdb.slick.user

import usermanager.domain.aggregates.user.User
import usermanager.infrastructure.rdb.slick.Tables._
import usermanager.query.user.UserView

trait RichUserSlick {

  implicit class RichUser(user: UsersRow) {
    def toEntity: User = {
      User(
        id = user.userId,
        userName = user.userName,
        email = user.email,
        password = user.password,
        status = user.status,
        versionNo = user.versionNo
      )
    }

    def toView: UserView = {
      UserView(
        id = user.userId,
        userName = user.userName,
        email = user.email,
        status = user.status,
        versionNo = user.versionNo
      )
    }
  }

}
