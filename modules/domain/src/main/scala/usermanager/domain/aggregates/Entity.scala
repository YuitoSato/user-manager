package usermanager.domain.aggregates

import usermanager.domain.types.{ Id, VersionNo }

trait Entity[SELF] {

  def id: Id[SELF]

  def versionNo: VersionNo[SELF]

}
