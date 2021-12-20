# Graveyard Builder Game - User-Model-Service

User Model Service for Graveyard Builder game. The final project of Ironhack Web Dev Bootcamp.

## Summary

This service is a RESTful API that allows users to create, read, update and delete their own user profiles. It is also used to fetch users for other services. It uses spring boot and [MySQL](https://www.mysql.com/) database.

This is a service used in the [Graveyard Builder Game](https://github.com/Joaodss/Graveyard-Builder-Game) project.

## Routes

On **PORT: 8100**

| Route Type | Route                                      | Description                                                                                                                               |
| :--------: | ------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------- |
|    GET     | /api/v1/users/all                          | Returns all users' details [**UserDTO**](#userdto).                                                                                       |
|    GET     | /api/v1/users/id/{{id}}                    | Returns [**UserDTO**](#userdto) by id. Requires id: long.                                                                                 |
|    GET     | /api/v1/users/auth/{{username}}            | Returns [**UserAuthDTO**](#userauthdto) by username. Requires username: string.                                                           |
|    GET     | /api/v1/users/username/{{username}}        | Returns [**UserDTO**](#userdto) by username. Requires username: string.                                                                   |
|    GET     | /api/v1/users/email/{{email}}              | Returns [**UserDTO**](#userdto) by email. Requires email: string.                                                                         |
|    GET     | /api/v1/users/partyLevel                   | Returns list of usernames between min and max party level. Request parameters: min(optional): int, max(optional): int.                    |
|    POST    | /api/v1/users/register                     | Registers new user, returns [**UserDTO**](#userdto). Requires body: [**RegisterUserDTO**](#registeruserdto).                              |
|    PUT     | /api/v1/users/update/{{username}}          | Updates user information by username, returns [**UserDTO**](#userdto). Requires username: string. Requires body: [**UserDTO**](#userdto). |
|    PUT     | /api/v1/users/update/{{username}}/password | Updates user password by username. Requires username: string. Requires body: [**NewPasswordDTO**](#newpassworddto) .                      |
|   DELETE   | /api/v1/users/delete/{{username}}          | Deletes user by username. Requires username: string.                                                                                      |

## Payloads

### UserDTO

```javascript
{
  "username": string,
  "email": string,
  "roles": string[],
  "profilePictureUrl": string,
  "experience": long,
  "gold": long,
  "partyLevel": int
}
```

### UserAuthDTO

```javascript
{
  "username": string,
  "password": string,
  "roles": string[]
}
```

### RegisterUserDTO

```javascript
{
  "username": string,
  "email": string,
  "password": string
}
```

### NewPasswordDTO

```javascript
{
  "newPassword": string
}
```

## Contacts

João Afonso Silva - [GitHub](https://github.com/Joaodss)
