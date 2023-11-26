# バリデーション課題

# 課題作成手順

- 各自課題作成用に [validation-task](https://github.com/reytech-co-jp/validation-task)をフォークし、リポジトリを作成
	- リポジトリ名: validation-tutorial-<名前>
- 1つの課題につき1つのブランチを切る
- 課題ごとにパッケージを分ける
	- 以下イメージ

```
validationtutorial/
┣━ task1/
┃ ┣━ UserController.java
┃ ┗━ UserRequest.java
┣━ task2/
┃ ┣━ HogeController.java
┃ ┗━ HogeRequest.java
┗━ ValidationApplication.java
```

- 各課題のテストコードまで作成する
	- 正常系及び全てのバリデーションの検証をおこなうこと
	- null及び空文字を禁止する項目については、両方の検証を行うこと
	- 文字数を指定する項目については、境界値テストを行うこと
- 環境
	- Java 17
	- Spring Boot 3.1.5

# 課題1

## バリデーション仕様

※詳細は[API仕様書](https://reytech-co-jp.github.io/validation-tutorial/)を確認してください

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
