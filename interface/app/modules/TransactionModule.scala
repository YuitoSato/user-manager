package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import usermanager.domain.transaction.async.{ AsyncTransactionBuilder, AsyncTransactionRunner }
import usermanager.domain.transaction.sync.{ SyncTransactionBuilder, SyncTransactionRunner }
import usermanager.infrastructure.cache.shade.transaction.async.{ AsyncShadeTransactionBuilder, AsyncShadeTransactionRunner }
import usermanager.infrastructure.cache.shade.transaction.sync.{ SyncShadeTransactionBuilder, SyncShadeTransactionRunner }
import usermanager.infrastructure.rdb.scalikejdbc.transaction.{ ScalikeJDBCTransactionBuilder, ScalikeJDBCTransactionRunner }
import usermanager.infrastructure.rdb.slick.transaction.{ SlickTransactionBuilder, SlickTransactionRunner }

class TransactionModule extends AbstractModule {

  override def configure(): Unit = {
    // SyncTransactionRunner
    bind(classOf[SyncTransactionRunner])
      .annotatedWith(Names.named("rdb.scalikejdbc"))
      .to(classOf[ScalikeJDBCTransactionRunner])
    bind(classOf[SyncTransactionRunner])
      .annotatedWith(Names.named("cache.shade"))
      .to(classOf[SyncShadeTransactionRunner])

    // SyncTransactionBuilder
    bind(classOf[SyncTransactionBuilder])
      .annotatedWith(Names.named("rdb.scalikejdbc"))
      .to(classOf[ScalikeJDBCTransactionBuilder])
    bind(classOf[SyncTransactionBuilder])
      .annotatedWith(Names.named("shade.cache"))
      .to(classOf[SyncShadeTransactionBuilder])

    // AsyncTransactionRunner
    bind(classOf[AsyncTransactionRunner])
      .annotatedWith(Names.named("shade.cache"))
      .to(classOf[AsyncShadeTransactionRunner])
    bind(classOf[AsyncTransactionRunner])
      .annotatedWith(Names.named("rdb.slick"))
      .to(classOf[SlickTransactionRunner])

    // AsyncTransactionBuilder
    bind(classOf[AsyncTransactionBuilder])
      .annotatedWith(Names.named("shade.cache"))
      .to(classOf[AsyncShadeTransactionBuilder])
    bind(classOf[AsyncTransactionBuilder])
      .annotatedWith(Names.named("shade.cache"))
      .to(classOf[SlickTransactionBuilder])

  }

}
