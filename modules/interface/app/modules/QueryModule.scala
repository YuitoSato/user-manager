package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.{ Configuration, Environment }
import usermanager.query.user.UserQuery
import usermanager.infrastructure.rdb.slick.user.UserQuerySlick

class QueryModule(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[UserQuery])
      .annotatedWith(Names.named("rdb.slick"))
      .to(classOf[UserQuerySlick])
  }

}
