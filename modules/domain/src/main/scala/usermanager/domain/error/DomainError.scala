package usermanager.domain.error

import usermanager.lib.error.Error

trait DomainError extends Error

object DomainError {

  case object Unauthorized extends DomainError {
    val code = "error.unauthorized"
    val message = s"Please login."
  }

}
