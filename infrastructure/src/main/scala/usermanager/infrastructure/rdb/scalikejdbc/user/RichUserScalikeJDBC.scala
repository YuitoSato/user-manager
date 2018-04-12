package usermanager.infrastructure.rdb.scalikejdbc.user

import usermanager.domain.aggregates.user.read.UserRead

trait RichUserScalikeJDBC {

  implicit class RichUser(user: Users) {
    def toEntity: UserRead = {
      UserRead(
        id = user.userId,
        userName = user.userName,
        email = user.email,
        password = user.password,
        status = user.status,
        versionNo = user.versionNo,
      )
    }
  }

}
