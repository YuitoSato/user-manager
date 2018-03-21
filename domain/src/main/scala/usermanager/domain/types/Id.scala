package usermanager.domain.types

//import scalaz.\/
import scalaz.syntax.std.ToOptionOps

case class Id[T](value: String) extends ToOptionOps {

//  /**
//    * idでDBを検索したときに存在しなかったらエラーを返すメソッド
//    * @param searchResult DB接続の結果
//    */
//  def assertExist(searchResult: Option[T]): Errors \/ T = {
//    searchResult \/> Errors.RecordNotFound(this.value.toString)
//  }
//
//  def hash(encrypt: String => String): HashedId[T] = {
//    val hashedId: HashedId[T] = encrypt(value.toString)
//    if (hashedId.isValid) {
//      hashedId
//    } else {
//      throw new Exception("Hash algorithm might be wrong.")
//    }
//  }
}

object Id {

  implicit def to[T](a: String): Id[T] = Id(a)
  implicit def from(b: Id[_]): String = b.value
//  implicit def iso[T]: Iso[Long, Id[T]] = Iso(to, from)
}
