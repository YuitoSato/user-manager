package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import controllers.di.{ Cache, RDB }
import play.api.{ Configuration, Environment }
import usermanager.domain.transaction.{ TransactionBuilder, TransactionRunner }
import usermanager.infrastructure.cache.shade.transaction.{ ShadeTransactionBuilder, ShadeTransactionRunner }
import usermanager.infrastructure.rdb.scalikejdbc.transaction.{ ScalikeJDBCTransactionBuilder, ScalikeJDBCTransactionRunner }
import usermanager.infrastructure.rdb.slick.transaction.{ SlickTransactionBuilder, SlickTransactionRunner }

class TransactionModule(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    // TransactionRunner
    bind(classOf[TransactionRunner])
      .annotatedWith(Names.named(RDB.Scalikejdbc))
      .to(classOf[ScalikeJDBCTransactionRunner])
    bind(classOf[TransactionRunner])
      .annotatedWith(Names.named(Cache.Shade))
      .to(classOf[ShadeTransactionRunner])
    bind(classOf[TransactionRunner])
      .annotatedWith(Names.named(RDB.Slick))
      .to(classOf[SlickTransactionRunner])

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
