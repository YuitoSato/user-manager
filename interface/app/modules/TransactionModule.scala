package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import di._
import play.api.{ Configuration, Environment }
import usermanager.domain.transaction.{ TransactionBuilder, TransactionRunner }
import usermanager.infrastructure.cache.shade.transaction.{ AsyncShadeTransactionBuilder, AsyncShadeTransactionRunner }
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
      .to(classOf[AsyncShadeTransactionRunner])
    bind(classOf[TransactionRunner])
      .annotatedWith(Names.named(RDB.Slick))
      .to(classOf[SlickTransactionRunner])

    // TransactionBuilder
    bind(classOf[TransactionBuilder])
      .annotatedWith(Names.named(RDB.Scalikejdbc))
      .to(classOf[ScalikeJDBCTransactionBuilder])
    bind(classOf[TransactionBuilder])
      .annotatedWith(Names.named(Cache.Shade))
      .to(classOf[AsyncShadeTransactionBuilder])
    bind(classOf[TransactionBuilder])
      .annotatedWith(Names.named(RDB.Slick))
      .to(classOf[SlickTransactionBuilder])
  }

}
