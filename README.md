# このリポジトリはなに？
ScalaでDDDを実践したり、テスタビリティの高いコードを書いてみたりするサンドボックス的な実験的なサンプルwebアプリ。
ユーザーの追加や、セッションの管理ができる

# レイヤー構成
上位の層は層一つ一つがsbtのプロジェクトとなるマルチプロジェクト構成。

- Interface層
- Application層
  - Service層
  - Scenario層
- Domain層
- Query層
- Infrastructure層


# Interface層
要するにControllerを指す。ユーザーからのインプットを受け取ったり、アウトプットを返す。DIもここで制御する。

# Application層
ユースケースを表現する層。リポジトリを呼び出したり、トランザクションを制御するのもこの層。書き込み処理にしか使わない。

## Service層
Transactionという外部リソースのIOを抽象化したモデルを返す。

Scenario層から再利用されることを想定しているのでTransactionを合成することは原則しない。

## Scenario層
Resultというひとつの処理の結果を抽象化したモデルを返す。
Service層のメソッドを複数呼び出してTransactionを合成してもよい。
原則再利用を想定しない。

# Domain層
エンティティと依存性逆転のためリポジトリインターフェイスを内包する。
CQRSの書き込み処理時にしか基本使わない

# Query層
CQRSの読み込み処理時にしか使わない層。Interface層から直接呼び出される

# Infrastructure層
Domain層のRespositoryとTransactionを実装する。
