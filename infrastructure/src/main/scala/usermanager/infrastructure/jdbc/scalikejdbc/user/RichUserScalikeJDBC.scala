package usermanager.infrastructure.jdbc.scalikejdbc.user

import usermanager.domain.user.User

trait RichUserScalikeJDBC {

  implicit class RichUser(user: Users) {
    def toDomain: User = {
      User(
        userId = user.userId,
        email = user.email,
        status = user.status,
        createdAt = user.createdAt,
        updatedAt = user.updatedAt,
        versionNo = user.versionNo
      )
    }
  }

  def userFromDomain(user: User): Users = {
    Users(
      userId = user.userId,
      email = user.email,
      status = user.status,
      createdAt = user.createdAt,
      updatedAt = user.updatedAt,
      versionNo = user.versionNo
    )
    Users(user.userId, )
  }

}
