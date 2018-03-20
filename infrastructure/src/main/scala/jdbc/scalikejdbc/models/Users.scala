package jdbc.scalikejdbc.models

import scalikejdbc._
import java.time.LocalDateTime

case class Users(
  userId: String,
  userName: String,
  password: String,
  email: String,
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime,
  versionNo: Int) {

  def save()(implicit session: DBSession = Users.autoSession): Users = Users.save(this)(session)

  def destroy()(implicit session: DBSession = Users.autoSession): Int = Users.destroy(this)(session)

}


object Users extends SQLSyntaxSupport[Users] {

  override val tableName = "USERS"

  override val columns = Seq("USER_ID", "USER_NAME", "PASSWORD", "EMAIL", "CREATED_AT", "UPDATED_AT", "VERSION_NO")

  def apply(u: SyntaxProvider[Users])(rs: WrappedResultSet): Users = apply(u.resultName)(rs)
  def apply(u: ResultName[Users])(rs: WrappedResultSet): Users = new Users(
    userId = rs.get(u.userId),
    userName = rs.get(u.userName),
    password = rs.get(u.password),
    email = rs.get(u.email),
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
    password: String,
    email: String,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
    versionNo: Int)(implicit session: DBSession = autoSession): Users = {
    withSQL {
      insert.into(Users).namedValues(
        column.userId -> userId,
        column.userName -> userName,
        column.password -> password,
        column.email -> email,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt,
        column.versionNo -> versionNo
      )
    }.update.apply()

    Users(
      userId = userId,
      userName = userName,
      password = password,
      email = email,
      createdAt = createdAt,
      updatedAt = updatedAt,
      versionNo = versionNo)
  }

  def batchInsert(entities: Seq[Users])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userId -> entity.userId,
        'userName -> entity.userName,
        'password -> entity.password,
        'email -> entity.email,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt,
        'versionNo -> entity.versionNo))
    SQL("""insert into USERS(
      USER_ID,
      USER_NAME,
      PASSWORD,
      EMAIL,
      CREATED_AT,
      UPDATED_AT,
      VERSION_NO
    ) values (
      {userId},
      {userName},
      {password},
      {email},
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
        column.password -> entity.password,
        column.email -> entity.email,
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
