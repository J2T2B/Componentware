# API Doku

|GET *`/api/ping`*  |
|---|
| 200: pong  |


| POST *`/api/register`*  |
|---|
|Body: Login Credentials |
| 204  |

| PUT *`/api/login`*  |
|---|
|Body: Login Credentials |
| 200: Session Token  |

|Login Credentials|
|---|
|username: String
|password: String
