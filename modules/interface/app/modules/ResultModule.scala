package modules

import com.google.inject.AbstractModule
import play.api.{ Configuration, Environment }
import builders.AsyncResultBuilder
import usermanager.domain.result.ResultBuilder

class ResultModule(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[ResultBuilder])
      .to(classOf[AsyncResultBuilder])
  }
}
