package usermanager.query.error

import usermanager.lib.error.Error
import usermanager.query.types.Id

trait QueryError extends Error

object QueryError {

  case class NotFound(entityType: String, entityId: Id[_]) extends QueryError {
    val code = "error.notFound"
    val message = s"type: $entityType, id: $entityId is not found."
  }

  case object EmailNotFound extends QueryError {
    val code = "error.emailNotFound"
    val message = "the requested email is not found."
  }

  case object EmailExists extends QueryError {
    val code = "error.emailExists"
    val message = "the requested email exists already."
  }

}

