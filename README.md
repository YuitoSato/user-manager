# このリポジトリはなに？
ScalaでDDDを実践したり、テスタビリティの高いコードを書いてみたりするサンドボックス的な実験的なサンプルwebアプリ。
ユーザーの追加や、セッションの管理ができます。

# レイヤー構成
上位の層は層一つ一つがsbtのプロジェクトとなるマルチプロジェクト構成。

- Interface層
- Application層
  - Service層
  - Scenario層
- Domain層
- Infrastructure層


# Interface層
要するにControllerを指す。ユーザーからのインプットを受け取ったり、アウトプットを返す。


ServiceとScenarioはここでDIをする。

# Application層
ユースケースを表現する層。

## Service層
Transactionという外部リソースのIOを抽象化したモデルを返す。

Scenario層から再利用されることを想定しているのでTransactionを合成することは原則しない。

## Scenario層
Resultというひとつの処理の結果を抽象化したモデルを返す。
Service層のメソッドを複数呼び出してTransactionを合成してもよい。
原則再利用を想定しない。

# Domain層
エンティティと依存性逆転のためリポジトリインターフェイスを内包する。

# Infrastructure層
Domain層のRespositoryとTransactionを実装する。
