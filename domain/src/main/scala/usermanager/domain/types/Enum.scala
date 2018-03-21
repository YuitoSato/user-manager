package usermanager.domain.types

trait Enum[A] {
  def value: A
}

trait EnumCompanion[A, B <: Enum[A]] {
  def values: Seq[B]
  def valueOf(value: A): B = values
    .find(_.value == value)
    // NOTE yuito: DBやCacheに保存されているEnum値が不正な値なときに起きる。
    // ユーザーは自己解決できないエラーのためEitherで返すことはせず、エラーを投げる。
    .getOrElse({
    throw new Exception(s"$value is invalid for Enum.")
  })
}
