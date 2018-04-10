package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.{ Configuration, Environment }
import usermanager.domain.aggregates.sessionuser.SessionUserRepository
import usermanager.domain.aggregates.user.read.UserReadRepository
import usermanager.domain.aggregates.user.write.UserWriteRepository
import usermanager.infrastructure.cache.shade.session.SessionUserRepositoryCache
import usermanager.infrastructure.rdb.scalikejdbc.user.{ UserReadRepositoryScalikeJDBC, UserWriteRepositoryScalikeJDBC }

class RepositoryModule(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    // UserReadRepository
    bind(classOf[UserReadRepository])
      .annotatedWith(Names.named("rdb.scalikejdbc"))
      .to(classOf[UserReadRepositoryScalikeJDBC])

    // UserWriteRepository
    bind(classOf[UserWriteRepository])
      .annotatedWith(Names.named("rdb.scalikejdbc"))
      .to(classOf[UserWriteRepositoryScalikeJDBC])

    // SessionUserRepository
    bind(classOf[SessionUserRepository])
      .annotatedWith(Names.named("cache.shade"))
      .to(classOf[SessionUserRepositoryCache])
  }

}
