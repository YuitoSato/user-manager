package modules

import com.google.inject.AbstractModule
import controllers.session.SessionScenarioImpl
import controllers.user.UserScenarioImpl
import play.api.{ Configuration, Environment }
import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.scenarios.user.UserScenario

class ScenarioModule(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[UserScenario])
      .to(classOf[UserScenarioImpl])
    bind(classOf[SessionScenario])
      .to(classOf[SessionScenarioImpl])
  }

}
