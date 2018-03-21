package usermanager.domain.user

import usermanager.domain.types.{ Email, Id }

trait UserRepository[F[_]] {

  def findById(userId: Id[User]): F[Option[User]]

  def findByEmail(email: Email[User]): F[Option[User]]

  def create(user: User): F[Unit]

  def update(user: User): F[Unit]

}
