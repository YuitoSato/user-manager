package usermanager.infrastructure.bcrypt.mindrot

import javax.inject.Inject

import org.mindrot.jbcrypt.BCrypt
import usermanager.domain.helpers.HashHelper
import usermanager.domain.types.{ HashedPassword, Password }

class JBcrypt @Inject() extends HashHelper {

  override def encrypt(source: String): String = BCrypt.hashpw(source, BCrypt.gensalt())

  override def checkPassword[T](plain: Password[T], hashed: HashedPassword[T]): Boolean = BCrypt.checkpw(plain, hashed)

}
