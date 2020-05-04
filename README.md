# semipro - セミプロ
~ 誰でもノーリスクでセミナーを開催できるサイト ~  

セミナー主催者は、セミナーの準備や開催場所施設の使用料金が  
セミナー受講費よりオーバーしてしまうリスクからセミナー開催に躊躇することがあります。  
等のサービスでは、セミナー開催人数が赤字のラインの場合、セミナー開催をキャンセルするように  
等サービスが制御する為、赤字になるリスクを低減しています。  
もちろん、目標人数に達しない場合でも、開催できるよう設定可能です。  
  
## システム構成
- semipro-web ユーザーにセミナーを告知します、また、セミナー主催者はセミナーの作成を行えます。  
- semipro-admin セミプロ運営者にて、セミナー主催者が登録したセミナーの審査を行います。  
- semipro-batch セミナー募集終了処理を行います。  
- semipro-domain セミプロのドメインロジックです。  
- semipro-common プロジェクト共通処理です。  

## 使用技術
- WEBフレームワークにSpringBootを使用しています。  
- Mavenマルチモジュール構成にてプロジェクトを構成しています。  
- セミナーチケット購入や、セミナーキャンセル時のキャッシュバック等の決済にはSprite APIを使用しています。  
- セミナーイメージ画像はaws S3に保持しています。  
- テストフレームワークにはSpock/groovyを使用しています。  
- 開発環境の構築にはdockerを使用しています。  
  
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
  
## CI/CD
本番環境へのデプロイにはcircleciにてビルド/テストを実行し  
aws codedeployにて本番環境にて自動デプロイしています。  
  
## 本番環境
本番環境にはcircleciにてビルドして作成された実行可能JarとapacheをAJPで連携して起動しています。  
本番環境は、VPC、EC2、ELBを組み合わせて構成しています。  
　　
## Demo
 [https://www.semipro.red/](https://www.semipro.red/) 
 
