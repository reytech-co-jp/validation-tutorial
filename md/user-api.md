FORMAT: 1A

# ユーザー管理API仕様書

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
