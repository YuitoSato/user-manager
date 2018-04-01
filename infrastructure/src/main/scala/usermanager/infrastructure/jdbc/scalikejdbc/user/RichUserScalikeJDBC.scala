package usermanager.infrastructure.jdbc.scalikejdbc.user

import usermanager.domain.user.read.UserRead

trait RichUserScalikeJDBC {

  implicit class RichUser(user: Users) {
    def toDomain: UserRead = {
      UserRead(
        id = user.userId,
        email = user.email,
        status = user.status,
        versionNo = user.versionNo
      )
    }
  }

//  def userFromDomain(user: AbstractUser): Users = {
//    Users(
//      userId = user.userId,
//      email = user.email,
//      status = user.status,
//      createdAt = user.createdAt,
//      updatedAt = user.updatedAt,
//      versionNo = user.versionNo
//    )
//    Users(user.userId, )
//  }

}
