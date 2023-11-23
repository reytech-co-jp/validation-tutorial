# バリデーション課題

# 課題作成手順

- 各自課題作成用のリポジトリを作成
	- リポジトリ名: validation-tutorial-<名前>
- 1つの課題につき1つのブランチを切る
- 課題ごとにディレクトリを分ける
- 各課題のテストコードまで作成する
- 環境
	- Java 17
	- Spring Boot 3.1.5

# 課題1

## バリデーション仕様

- username
	- null及び空文字禁止
	- 3文字以上20文字以下
- password
	- null及び空文字禁止
	- 3文字以上20文字以下
- confirmPassword
	- passwordとconfirmPasswordが一致（相関チェック）

## CI（GitHub Actions）の導入

- テストの実行
- Slackへの通知
	- 通知先: dev_notification
	- 参考: https://github.com/renangton/task5_crud_read_and_create/blob/main/.github/workflows/test-ci.yml
