package usermanager.domain.error

import usermanager.domain.types.Id

sealed trait DomainError extends AbstractError

object DomainError {

//  trait Unexpected extends Errors
  case class Unexpected(msg: String) extends DomainError {
    val code = "error.unexpected"
    val message: String = msg
  }

  object Unexpected {
    def apply(t: Throwable): Unexpected = new Unexpected(t.toString)
  }

  case class NotFound(entityType: String, entityId: Id[_]) extends DomainError {
    val code = "error.notFound"
    val message = s"type: $entityType, id: $entityId is not found"
  }

  case class BadRequest(msg: String) extends DomainError {
    val code = "error.badRequest"
    val message: String = msg
  }

  case object InvalidDISetting {
    val code = "error.InvalidDI"
    val message = "DI Setting is invalid"
  }


//
//  case class JsonError(error: JsError) extends Errors {
//    val code = "error.json"
//    val message = s"Json error occured. error=$error"
//  }
//
//  case class EmailExistsError(email: Email[_]) extends Errors {
//    val code = "error.emailExists"
//    val message = s"Email already exists. email=${email.value}"
//  }
//
//  case class RecordNotFound(searchElement: String) extends Errors {
//    val code = "error.recordNotFound"
//    val message = s"Record not found by $searchElement"
//  }
//
//  case class UpdateFailure(updateElement: Any) extends Errors {
//    val code = "error.updateFailure"
//    val message = s"Failed to update by ${updateElement.toString}"
//  }
//
//  case class DeleteFailure(deleteElement: Any) extends Errors {
//    val code = "error.deleteFailure"
//    val message = s"Failed to delete by ${deleteElement.toString}"
//  }
//
//  case object CantDeleteInitialAccountFollowing extends Errors {
//    val code = "error.cantDeleteInitialAccountFollowing"
//    val message = "Initial account following record cannot be deleted."
//  }

  case object Unauthorized extends DomainError {
    val code = "error.unauthorized"
    val message = s"Please login."
  }
//
//  case object InvalidAccountIds extends Errors {
//    val code = "error.invalidAccountIds"
//    val message = "Some of account IDs re invalid."
//  }
//
//  case object InvalidPassword extends Errors {
//    val code = "error.invalidPassword"
//    val message = "Password is invalid."
//  }
//
//  case object InvalidFollowerId extends Errors {
//    val code = "error.invalidFollowerId"
//    val message = "Follower ID is invalid."
//  }
//
//  case object AlreadySignIn extends Errors {
//    val code = "error.alreadySignIn"
//    val message = "User has session."
//  }
//
//  case class InvalidPasswordOrEmail(email: Email[_]) extends Errors {
//    val code = "error.invalidPasswordOrEmail"
//    val message = s"Password or email is invalid. email=${email.value}"
//  }
//
//  case object WrongVersionNo extends Errors {
//    val code = "error.wrongVersionNo"
//    val message = "VersionNo is wrong."
//  }
}
