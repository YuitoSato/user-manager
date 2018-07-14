package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import controllers.di.{ Cache, RDB }
import play.api.{ Configuration, Environment }
import usermanager.infrastructure.cache.shade.transaction.ShadeTransactionBuilder
import usermanager.infrastructure.rdb.scalikejdbc.transaction.ScalikeJDBCTransactionBuilder
import usermanager.infrastructure.rdb.slick.transaction.SlickTransactionBuilder
import usermanager.lib.transaction.TransactionBuilder

class TransactionModule(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    // TransactionBuilder
    bind(classOf[TransactionBuilder])
      .annotatedWith(Names.named(RDB.Scalikejdbc))
      .to(classOf[ScalikeJDBCTransactionBuilder])
    bind(classOf[TransactionBuilder])
      .annotatedWith(Names.named(Cache.Shade))
      .to(classOf[ShadeTransactionBuilder])
    bind(classOf[TransactionBuilder])
      .annotatedWith(Names.named(RDB.Slick))
      .to(classOf[SlickTransactionBuilder])
  }

}
