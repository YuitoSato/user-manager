package serializers

import play.api.libs.json.{ Json, Writes }
import usermanager.query.user.UserView

trait UserViewSerializer extends TypeSerializer {

  implicit val userViewWrites: Writes[UserView] = Json.writes[UserView]

}
