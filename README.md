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
	- 正常系及び全てのバリデーションの検証を行うこと
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

# 課題2

## バリデーション仕様

※詳細は[API仕様書](https://reytech-co-jp.github.io/validation-tutorial/)を確認してください

- productName(String)
	- null及び空文字、半角スペース禁止
	- 2文字以上20文字以下
- category(String)
	- null及び空文字、半角スペース禁止
	- Electronics, Clothing, Booksのみ許可する
		- 大文字小文字の違いは無視する
- price(Integer)
	- null禁止
	- 0より大きい
	- 1,000,000以下

## その他条件
- カテゴリーの種類はEnumで定義すること
- バリデーションメッセージはValidationMessages.propertiesを使用すること
```
#NotBlank
E0001=入力してください
#NotNull
E0002=入力してください
#Size
E0003={min}文字以上{max}文字以下である必要があります
#Positive
E0004=0より大きい値である必要があります
#Max
E0005={value}以下である必要があります
#ValidCategory
ValidCategory=無効なカテゴリです
```
