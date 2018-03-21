package usermanager.infrastructure.cache.shade

import play.api.libs.json.{ Json, Reads, Writes }
import shade.memcached.{ Configuration, Memcached }

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.Duration

class ShadeCache (
  implicit ec: ExecutionContext
) {

  // TODO application.confから読み込めるようにする。
  val memcached = Memcached(Configuration("127.0.0.1:11211"))

  def getString(key: String): Option[String] = {
    memcached.awaitGet[String](key)
  }

  def getJson[A](key: String)(implicit reads: Reads[A]): Option[A] = {
    getString(key).flatMap(Json.parse(_).asOpt[A])
  }

  def setJson[A](key: String, value: A, timeout: Duration)(implicit writes: Writes[A]): Unit = {
    memcached.awaitSet(key, Json.toJson(value).toString(), timeout)
  }

  def delete(key: String): Unit = {
    memcached.delete(key)
  }
}
