FORMAT: 1A

# バリデーション課題API仕様書

# Group Users

## POST /users

ユーザーを登録します。
以下のパラメータをJSON形式で送信します。

+ username (string, required) - ユーザー名。3文字以上、20文字以内。
+ password (string, required) - パスワード。8文字以上、30文字以内。
+ confirmPassword (string) - 確認用パスワード。パスワードと一致。

+ Request Sample

```
curl -X POST 'http://localhost:8080/users' \
-H 'Content-Type: application/json' \
-d '{
    "username": "user",
    "password": "password",
    "confirmPassword": "password"
}' 
```

+ Request (application/json)

	      {
	         "username": "user",
	         "password": "password",
	         "confirmPassword": "password"
	      }

+ Response 201 (application/json)
	+ Body
		{
			"message": "successfully created"
		}

+ Response 400
	+ Body
		{
			"status":"BAD_REQUEST"
			"message":"validation error",
			"errors":[
				{
					"field":"username",
					"messages":[
						"ユーザー名を入力してください",
						"ユーザー名は3文字以上20文字以下である必要があります"
					]
				},
				{
					"field":"password",
					"messages":[
						"パスワードを入力してください",
						"パスワードは8文字以上30文字以下である必要があります"
					]
				},
				{
					"field":"passwordMatching",
					"messages":[
						"パスワードと確認用パスワードが一致していません"
					]
				}
			]
		}

# Group Products

## POST /products

商品を登録します。
以下のパラメータをJSON形式で送信します。

+ productName (string, required) - 商品名。3文字以上、20文字以内。
+ category (string, required) - カテゴリー。Electronics, Clothing, Booksのみ許可。
+ price (integer, required) - 価格。0より大きい。1000000以下。

+ Request Sample

```
curl -X POST 'http://localhost:8080/products' \
-H 'Content-Type: application/json' \
-d '{
    "productName": "iPhone15",
    "category": "Electronics",
    "price": "150000"
}' 
```

+ Request (application/json)

	      {
	         "productName": "iPhone15",
	         "category": "Electronics",
	         "price": "150000"
	      }

+ Response 201 (application/json)
	+ Body
		{
			"message": "successfully created"
		}

+ Response 400
	+ Body
		{
			"status":"BAD_REQUEST"
			"message":"validation error",
			"errors":[
				{
					"field":"productName",
					"messages":[
						"商品名を入力してください",
						"商品名は2文字以上20文字以下である必要があります"
					]
				},
				{
					"field":"category",
					"messages":[
						"カテゴリを入力してください",
						"無効なカテゴリです"
					]
				},
				{
					"field":"price",
					"messages":[
						"価格を入力してください",
						"価格は0より大きい値である必要があります",
						"価格は1000000以下である必要があります"
					]
				}
			]
		}