package modules

import com.google.inject.AbstractModule
import controllers.session.SessionServiceImpl
import controllers.user.UserServiceImpl
import play.api.{ Configuration, Environment }
import usermanager.application.services.session.SessionService
import usermanager.application.services.user.UserService

class ServiceModule(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[UserService])
      .to(classOf[UserServiceImpl])
    bind(classOf[SessionService])
      .to(classOf[SessionServiceImpl])
  }

}
