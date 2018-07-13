package controllers

package object di {

  case object RDB {
    final val Slick = "rdb.slick"
    final val Scalikejdbc = "rdb.scalikejdbc"
  }

  case object Cache {
    final val Shade = "cache.shade"
  }

}
