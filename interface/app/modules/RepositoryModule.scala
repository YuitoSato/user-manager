package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.{ Configuration, Environment }
import usermanager.domain.aggregates.sessionuser.SessionRepository
import usermanager.domain.aggregates.user.UserRepository
import usermanager.infrastructure.cache.shade.session.SessionRepositoryCache
import usermanager.infrastructure.rdb.scalikejdbc.user.UserRepositoryScalikeJDBC
import usermanager.infrastructure.rdb.slick.user.UserRepositorySlick

class RepositoryModule(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    // UserRepository
    bind(classOf[UserRepository])
      .annotatedWith(Names.named("rdb.scalikejdbc"))
      .to(classOf[UserRepositoryScalikeJDBC])
    bind(classOf[UserRepository])
      .annotatedWith(Names.named("rdb.slick"))
      .to(classOf[UserRepositorySlick])

    // SessionUserRepository
    bind(classOf[SessionRepository])
      .annotatedWith(Names.named("cache.shade"))
      .to(classOf[SessionRepositoryCache])
  }

}
