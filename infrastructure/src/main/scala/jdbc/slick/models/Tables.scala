package jdbc.slick.models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Users
   *  @param userId Database column USER_ID SqlType(CHAR), PrimaryKey, Length(36,false)
   *  @param userName Database column USER_NAME SqlType(VARCHAR), Length(50,true)
   *  @param password Database column PASSWORD SqlType(CHAR), Length(60,false)
   *  @param email Database column EMAIL SqlType(VARCHAR), Length(100,true)
   *  @param createdAt Database column CREATED_AT SqlType(DATETIME)
   *  @param updatedAt Database column UPDATED_AT SqlType(DATETIME)
   *  @param versionNo Database column VERSION_NO SqlType(INT) */
  final case class UsersRow(userId: String, userName: String, password: String, email: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, versionNo: Int)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[String], e1: GR[java.sql.Timestamp], e2: GR[Int]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[Int]))
  }
  /** Table description of table USERS. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, Some("user_manager"), "USERS") {
    def * = (userId, userName, password, email, createdAt, updatedAt, versionNo) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(userId), Rep.Some(userName), Rep.Some(password), Rep.Some(email), Rep.Some(createdAt), Rep.Some(updatedAt), Rep.Some(versionNo)).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column USER_ID SqlType(CHAR), PrimaryKey, Length(36,false) */
    val userId: Rep[String] = column[String]("USER_ID", O.PrimaryKey, O.Length(36,varying=false))
    /** Database column USER_NAME SqlType(VARCHAR), Length(50,true) */
    val userName: Rep[String] = column[String]("USER_NAME", O.Length(50,varying=true))
    /** Database column PASSWORD SqlType(CHAR), Length(60,false) */
    val password: Rep[String] = column[String]("PASSWORD", O.Length(60,varying=false))
    /** Database column EMAIL SqlType(VARCHAR), Length(100,true) */
    val email: Rep[String] = column[String]("EMAIL", O.Length(100,varying=true))
    /** Database column CREATED_AT SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("CREATED_AT")
    /** Database column UPDATED_AT SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("UPDATED_AT")
    /** Database column VERSION_NO SqlType(INT) */
    val versionNo: Rep[Int] = column[Int]("VERSION_NO")
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
