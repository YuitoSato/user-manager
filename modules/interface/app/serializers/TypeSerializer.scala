package serializers

import play.api.libs.json._
import usermanager.query.types._
import usermanager.query.types.enums.Status

trait TypeSerializer {

  implicit def idWrites[A]: Writes[Id[A]] = Json.writes[Id[A]]
  implicit def nameWrites[A]: Writes[Name[A]] = Json.writes[Name[A]]
  implicit def createdAtWrites[A]: Writes[CreatedAt[A]] = Json.writes[CreatedAt[A]]
  implicit def versionNoWrites[A]: Writes[VersionNo[A]] = Json.writes[VersionNo[A]]
  implicit def EmailWrites[A]: Writes[Email[A]] = Json.writes[Email[A]]

  implicit def statusWrites: Writes[Status] = new Writes[Status] {
    def writes(status: Status): JsValue = {
      JsString(status.value)
    }
  }

}

