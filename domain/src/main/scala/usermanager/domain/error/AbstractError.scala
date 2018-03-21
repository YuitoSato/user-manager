package usermanager.domain.error

trait AbstractError {

  def code: String
  def message: String

}
