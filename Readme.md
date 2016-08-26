# AWSのAccess Key の為のユーティリティ集

## モジュール

* library ... アプリケーション本体
* create ... 複数ユーザ分の Access Key を発行し、credentialsファイルを生成するSpring Boot の CLIアプリケーション
* rotate ... 自分のAccess Key をローテーションする Spring Boot の CLIアプリケーション

## 事前準備

* AWSにアクセスできるようにして下さい。
* createを使う場合、他ユーザの Access Key に対する iam:createAccessKey の権限が必要です
* rotateを使う場合、iam:listAccessKeys,iam:createAccessKey,iam:updateAccessKey,iam:deleteAccessKey の権限が必要です

## 使い方

### create

```
% java -jar aaaa.jar user1 user2 user3
```

### rotate

```
% java -jar bbbb.jar
```

rotateコマンドは、

1. 既存の無効化されたアクセスキーを削除
2. 新しいアクセスキーを発行
3. ~/.aws/backup/ディレクトリに、~/.aws/credentials のコピーを作成
4. 新しいアクセスキーで、~/.aws/credentials を上書き（ファイルそのものを上書きするので注意）
5. 古いアクセスキーを無効化
