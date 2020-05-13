# semipro - セミプロ
~ 誰でもノーリスクでセミナーを開催できるサイト ~  

セミナー主催者は、セミナーの準備や開催場所施設の使用料金が  
セミナー受講費よりオーバーしてしまうリスクからセミナー開催に躊躇することがあります。  
等のサービスでは、セミナー開催人数が赤字のラインの場合、セミナー開催をキャンセルするように  
等サービスが制御する為、赤字になるリスクを低減しています。  
もちろん、目標人数に達しない場合でも、開催できるよう設定可能です。  
  
## システム構成
- semipro-web  
ユーザーは開催中のセミナーの閲覧、申し込みが行えます。  
セミナー主催者は開催するセミナーを登録します。  
- semipro-admin  
セミプロ運営者にて、セミナー主催者が登録したセミナーの審査を行います。  
審査時はsemipro-webのセミナー登録時に登録する本人確認情報を元にStripアカウントの登録を行います。
- semipro-batch  
セミナー募集終了処理を行います。  
参加人数により開催決定の成否をセミナー主催者と参加者にメール送信します。
- semipro-domain  
セミプロのドメインロジックです。  
- semipro-common  
プロジェクト共通処理です。  

## 使用技術
- WEBフレームワークにSpringBootを使用しています。  
- Mavenマルチモジュール構成にてプロジェクトを構成しています。  
- セミナーチケット購入や、セミナーキャンセル時のキャッシュバック等の決済にはSprite APIを使用しています。  
- セミナーイメージ画像はaws S3に保持しています。  
- テストフレームワークにはSpock/groovyを使用しています。  
- 開発環境の構築にはdockerを使用しています。  
- CSSフレームワークには、uikitを使用しています。
  
## 動作環境
Java: 11  
SpringBoot: 2.2.6.RELEASE  
MySQL: 8.0.15  
  
## 開発環境構築
docker-compose up --build  
  
### 環境変数
- SMTP_USER=red.semipro@gmail.com  
- SMTP_PASSWORD=別途ご連絡します  
- DB_USER=root  
- DB_PASSWORD=別途ご連絡します  
- STRIPE_API_KEY=別途ご連絡します  
- AWS_SECRET_ACCESS_KEY=別途ご連絡します  
- AWS_ACCESS_KEY_ID=別途ご連絡します  
- AWS_DEFAULT_REGION=ap-northeast-1
  
## CI(Continuous Integration)  
CircleCIサービスにてビルド/テストを自動化しています。  
成果物はS3にアップロードしています。  
[.circleci/congig.yml](https://github.com/fujii-daisuke/semipro/blob/master/.circleci/config.yml)
  
## CD(Continuous Delivery)  
本番環境へのデプロイにはAWS CodeDeployを使用しています。  
S3にアップロードされた成果物を本番環境へデプロイしています。  
apache停止、tomcat停止、成果物の入れ替え、tomcat起動、apache起動の順序で行なっています。  
[.codedeploy/appspec.yml](https://github.com/fujii-daisuke/semipro/blob/master/.codedeploy/appspec.yml)
  
## 本番環境  
本番環境は、ALB、VPC、EC2を組み合わせて構成しています。  
１台のEC2にWEB、管理、バッチアプリを設置しています。  
apacheのバーチャルホストにてWEB、管理アプリへのアクセスを制御しAJP通信にて
各アプリの実行可能Jarより起動したtomcatと連携しています。  
　　
## Demo
- WEB  
 [https://www.semipro.red/](https://www.semipro.red/)  
  - 主催者  
  ログインEmail: demo.semipro@gmail.com  
  パスワード: demo.semipro  
  - 参加者  
  ログインEmail: customer.semipro@gmail.com  
  パスワード: 2wsx'UJM  
  
- 管理  
 [https://admin.semipro.red/](https://admin.semipro.red/)  
 ログインID: demo  
 パスワード: 2wsx$RFV

