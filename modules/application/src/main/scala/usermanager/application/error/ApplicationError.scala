package usermanager.application.error

import usermanager.domain.types.Id
import usermanager.lib.error.Error

trait ApplicationError extends Error

object ApplicationError {

  case class NotFound(entityType: String, entityId: Id[_]) extends ApplicationError {
    val code = "error.notFound"
    val message = s"type: $entityType, id: $entityId is not found."
  }

  case object EmailNotFound extends ApplicationError {
    val code = "error.emailNotFound"
    val message = "the requested email is not found."
  }

  case object EmailExists extends ApplicationError {
    val code = "error.emailExists"
    val message = "the requested email exists already."
  }







}
