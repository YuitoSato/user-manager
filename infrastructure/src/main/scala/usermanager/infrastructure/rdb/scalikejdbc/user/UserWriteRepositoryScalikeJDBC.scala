package usermanager.infrastructure.rdb.scalikejdbc.user

import javax.inject.Inject

import usermanager.domain.aggregates.user.write.{ UserWrite, UserWriteRepository }

class UserWriteRepositoryScalikeJDBC @Inject() extends UserWriteRepository
  with RichUserScalikeJDBC
{
  override def create(user: UserWrite) = ???

  override def update(user: UserWrite) = ???

}
