package usermanager.domain.transaction

import scala.concurrent.Future

/**
  * Taskを実行する
  * トランザクションオブジェクトの型ごとにインスタンスを作成すること
  *
  * @tparam Resource トランザクションオブジェクトの型
  */
trait TaskRunner[Resource] {
  /**
    * Taskを実行する
    *
    * @param task 実行するTask
    * @tparam A Task実行すると得られる値の型
    * @return Task実行して得られた値
    */
  def run[A](task: Task[Resource, A]): Future[A]
}
