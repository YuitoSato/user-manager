package usermanager.domain.helpers

import usermanager.domain.types.{ HashedPassword, Password }

trait HashHelper {

  def encrypt(source: String): String

  def checkPassword[T](plain: Password[T], hashed: HashedPassword[T]): Boolean

}
