package usermanager.domain.helpers

import java.util.UUID

class UUIDGenerator {

  def createId: String = {
    UUID.randomUUID.toString
  }

}
