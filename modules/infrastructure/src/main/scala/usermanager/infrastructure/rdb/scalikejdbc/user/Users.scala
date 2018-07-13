package usermanager.infrastructure.rdb.scalikejdbc.user

import java.time.LocalDateTime

import scalikejdbc._

case class Users(
  userId: String,
  userName: String,
  email: String,
  password: String,
  status: String,
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime,
  versionNo: Int) {

  def save()(implicit session: DBSession = Users.autoSession): Users = Users.save(this)(session)

  def destroy()(implicit session: DBSession = Users.autoSession): Int = Users.destroy(this)(session)

}


object Users extends SQLSyntaxSupport[Users] {

  override val tableName = "USERS"

  override val columns = Seq("USER_ID", "USER_NAME", "EMAIL", "PASSWORD", "STATUS", "CREATED_AT", "UPDATED_AT", "VERSION_NO")

  def apply(u: SyntaxProvider[Users])(rs: WrappedResultSet): Users = apply(u.resultName)(rs)
  def apply(u: ResultName[Users])(rs: WrappedResultSet): Users = new Users(
    userId = rs.get(u.userId),
    userName = rs.get(u.userName),
    email = rs.get(u.email),
    password = rs.get(u.password),
    status = rs.get(u.status),
    createdAt = rs.get(u.createdAt),
    updatedAt = rs.get(u.updatedAt),
    versionNo = rs.get(u.versionNo)
  )

  val u = Users.syntax("u")

  override val autoSession = AutoSession

  def find(userId: String)(implicit session: DBSession = autoSession): Option[Users] = {
    withSQL {
      select.from(Users as u).where.eq(u.userId, userId)
    }.map(Users(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Users] = {
    withSQL(select.from(Users as u)).map(Users(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Users as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Users] = {
    withSQL {
      select.from(Users as u).where.append(where)
    }.map(Users(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Users] = {
    withSQL {
      select.from(Users as u).where.append(where)
    }.map(Users(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Users as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: String,
    userName: String,
    email: String,
    password: String,
    status: String,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
    versionNo: Int)(implicit session: DBSession = autoSession): Users = {
    withSQL {
      insert.into(Users).namedValues(
        column.userId -> userId,
        column.userName -> userName,
        column.email -> email,
        column.password -> password,
        column.status -> status,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt,
        column.versionNo -> versionNo
      )
    }.update.apply()

    Users(
      userId = userId,
      userName = userName,
      email = email,
      password = password,
      status = status,
      createdAt = createdAt,
      updatedAt = updatedAt,
      versionNo = versionNo)
  }

  def batchInsert(entities: Seq[Users])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userId -> entity.userId,
        'userName -> entity.userName,
        'email -> entity.email,
        'password -> entity.password,
        'status -> entity.status,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt,
        'versionNo -> entity.versionNo))
    SQL("""insert into USERS(
      USER_ID,
      USER_NAME,
      EMAIL,
      PASSWORD,
      STATUS,
      CREATED_AT,
      UPDATED_AT,
      VERSION_NO
    ) values (
      {userId},
      {userName},
      {email},
      {password},
      {status},
      {createdAt},
      {updatedAt},
      {versionNo}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Users)(implicit session: DBSession = autoSession): Users = {
    withSQL {
      update(Users).set(
        column.userId -> entity.userId,
        column.userName -> entity.userName,
        column.email -> entity.email,
        column.password -> entity.password,
        column.status -> entity.status,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.versionNo -> entity.versionNo
      ).where.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: Users)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Users).where.eq(column.userId, entity.userId) }.update.apply()
  }

}
