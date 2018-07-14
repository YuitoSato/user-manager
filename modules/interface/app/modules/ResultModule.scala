package modules

import builders.AsyncResultBuilder
import com.google.inject.AbstractModule
import play.api.{ Configuration, Environment }
import usermanager.lib.result.ResultBuilder

class ResultModule(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[ResultBuilder])
      .to(classOf[AsyncResultBuilder])
  }

}
