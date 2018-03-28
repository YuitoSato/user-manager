package usermanager.domain.transaction

trait Transaction

trait ReadTransaction extends Transaction

trait ReadWriteTransaction extends ReadTransaction
