package modules

import com.google.inject.AbstractModule
import usermanager.domain.helpers.HashHelper
import usermanager.infrastructure.bcrypt.mindrot.JBcrypt

class HashHelperModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[HashHelper])
      .to(classOf[JBcrypt])
  }

}
