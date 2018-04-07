package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import usermanager.domain.helpers.HashHelper
import usermanager.infrastructure.bcrypt.mindrot.JBcrypt

class HashHelperModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[HashHelper])
      .annotatedWith(Names.named("cache.shade"))
      .to(classOf[JBcrypt])
  }

}
