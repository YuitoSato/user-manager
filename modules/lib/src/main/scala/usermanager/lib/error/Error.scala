package usermanager.lib.error

trait Error {

  def code: String
  def message: String

  val logging: Boolean = false

  protected val stackTrace: Array[StackTraceElement] = {
    val traces = Thread.currentThread().getStackTrace
    traces.drop(traces.lastIndexWhere(t => t.getClassName == getClass.getName) + 1)
  }

  override def toString: String = {
    s"code: $code\nmessage: $message" +
    s"""${getClass.getName}
       |${stackTrace.map(s => s"  at $s").mkString("\n")}
    """.stripMargin
  }

}

object Error {

  case class Unexpected(msg: String) extends Error {
    val code = "error.unexpected"
    val message: String = msg
    override val logging: Boolean = true
  }

  object Unexpected {
    def apply(t: Throwable): Unexpected = Unexpected(t.toString)
  }

}
