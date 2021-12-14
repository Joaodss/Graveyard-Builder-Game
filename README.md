# Graveyard Builder Game

Final project for Ironhack Web Dev Bootcamp.

## Summary

## Table Of Contents

## About The Project

This project was created as the final project for the Ironhack Web Dev Bootcamp. It's a web application game based on one of the my fist java group project, and inspired by games like pokemon and RPG games with some roguelike elements.

The project is divided in multiple microservices and a client application:

- [Graveyard-Builder-Game-Client](https://github.com/Joaodss/Graveyard-Builder-Game-Client)
- [Graveyard-Builder-Game-Discovery-Service](https://github.com/Joaodss/Graveyard-Builder-Game-Discovery-Service)
- [Graveyard-Builder-Game-Gateway-Service](https://github.com/Joaodss/Graveyard-Builder-Game-Gateway-Service)
- [Graveyard-Builder-Game-User-Model-Service](https://github.com/Joaodss/Graveyard-Builder-Game-User-Model-Service)
- [Graveyard-Builder-Game-Character-Model-Service](https://github.com/Joaodss/Graveyard-Builder-Game-Character-Model-Service)
- [Graveyard-Builder-Game-Opponent-Selection-Service](https://github.com/Joaodss/Graveyard-Builder-Game-Opponent-Selection-Service)
- [Graveyard-Builder-Game-Battle-Service](https://github.com/Joaodss/Graveyard-Builder-Game-Battle-Service)
- [Graveyard-Builder-Game-Party-Manager-Service](https://github.com/Joaodss/Graveyard-Builder-Game-Party-Manager-Service)
- [Graveyard-Builder-Game-Profile-Service](https://github.com/Joaodss/Graveyard-Builder-Game-Profile-Service)
- [Graveyard-Builder-Game-SignIn-Service](https://github.com/Joaodss/Graveyard-Builder-Game-SignIn-Service)
- [Graveyard-Builder-Game-Auth-Service](https://github.com/Joaodss/Graveyard-Builder-Game-Auth-Service)

### Project Requirements

Your project must meet all of the requirements below:

- Include a microservices Java/Spring Boot back-end and an Angular frontend.
- Include at least 2 SQL database tables.
- Include at least 4 services plus at least 1 edge service.
- Include at least 1 GET, POST, PUT/PATCH, and DELETE route.
- Include adequate and complete documentation.

### The game

Graveyard Builder Game consists of a single player browser game. The players must build their party of warriors, archers, and mages, defeat other players, gain gold, and level up. Each character type has its own unique stats and abilities. When characters die, they will be moved to the graveyard. To add them back to the party, the player must revive them with gold.

**The game is temporary available at http://graveyardbuildergame.site**

#### Features

- 3 classes of characters: Warrior, Archer, Mage
- Automatic opponent generation
- Random option on character creation
- Customizable characters and user pictures
- Battle other players' teams
- Help page
- Mobile friendly interface
- JWT authentication

### Built With

Technologies used in this project:

- [Java](https://www.java.com/)
- [Spring](https://spring.io/)
- [MySQL](https://www.mysql.com/)
- [Node.js](https://nodejs.org/en/)
- [Angular](https://angular.io/)
- [PostMan](https://www.getpostman.com/)
- [GitHub](https://github.com/)
- [Linode](https://www.linode.com/)
- [LucidChart](https://www.lucidchart.com/)

## Getting Started

The project contains separate microservices for the backend and a client frontend part. To locally run the web application all parts must be operational.

### Setup - Backend Microservices

- Clone the backend repositories:
  - https://github.com/Joaodss/Graveyard-Builder-Game-Discovery-Service
  - https://github.com/Joaodss/Graveyard-Builder-Game-Gateway-Service
  - https://github.com/Joaodss/Graveyard-Builder-Game-User-Model-Service
  - https://github.com/Joaodss/Graveyard-Builder-Game-Character-Model-Service
  - https://github.com/Joaodss/Graveyard-Builder-Game-Opponent-Selection-Service
  - https://github.com/Joaodss/Graveyard-Builder-Game-Battle-Service
  - https://github.com/Joaodss/Graveyard-Builder-Game-Party-Manager-Service
  - https://github.com/Joaodss/Graveyard-Builder-Game-Profile-Service
  - https://github.com/Joaodss/Graveyard-Builder-Game-SignIn-Service
  - https://github.com/Joaodss/Graveyard-Builder-Game-Auth-Service
  - https://github.com/EN-IH-WDPT-JUN21/CSharks-BackEndHomework
- Setup the following database name and user, or setup your own database by changing the values in the `application.properties` file

```sql
CREATE DATABASE GraveyardBuilder;

CREATE USER 'ironhacker'@'localhost' IDENTIFIED BY '1r0n-H4ck3r';
GRANT ALL PRIVILEGES ON GraveyardBuilder.* TO 'ironhacker'@'localhost';
FLUSH PRIVILEGES;
```

- Run the following command to start the each spring application: `mvn spring-boot:run`, or by using an IDE like IntelliJ IDEA
- The application will be available by using the gateway service at: [http://localhost:8000/](http://localhost:8000/)

### Setup - Frontend Application

- Clone the frontend repository: https://github.com/Joaodss/Graveyard-Builder-Game-Client
- Install the dependencies: `npm i`
- Run the following command to start the frontend application: `npm start`
- The application will be available at: [http://localhost:4200/](http://localhost:4200/)

## Backend API

### Security

There is an admin pre generated profiles which can be used for managing the users and fixing possible user problems.

**Admin:**

```javascript
username: admin;
password: ironhack - admin;
```

This project implements a [JWT](https://jwt.io/) authentication mechanism. The JWT is generated by the backend and sent to the frontend. The frontend uses the JWT to authenticate the user and retrieve information.

To authenticate the user/admin manually (with postman or other tool) you must follow this steps:

- To login with the pretended profile send a post request to the login page: [http://localhost:8000/login](http://localhost:8000/login);
- In the body of the request add the `x-www-form-urlencoded` format and add the following values:
  | key | value |
  | ---------- | ----------------- |
  | `username` | `<username here>` |
  | `password` | `<password here>` |

- The response will contain a JWT token and an expiration date.
- To access the protected routes you must add the JWT token to the `Authorization` header of the request with the following format:
  | key | value |
  | --------------- | --------------------- |
  | `Authorization` | `Bearer <token here>` |

### Routes

All rotes available are managed by the Gateway Service on port 8000. For more information on the specific microservices routes, please refer to the documentation of the microservices.

#### Public Access

#### Registered User Access

#### Admin Access

### DTOs

## Frontend Application

## Project Diagrams

![UML Class Diagram](diagrams/UML%20Class%20Diagram.png)

![UML Microservices Diagram](diagrams/Microservices%20UML%20Structure.png)

![Microservices Endpoints](diagrams/Microservices%20Endpoints.png)

## Roadmap

- Complete backend testing;
- Refactor JWT security in microservices;
- Optimize opponent AI actions;
- Animate battle;
- Enhance design;
- Deploy to AWS;
- Add Facebook login and friends integration;
- Online P2P battles

## Contacts

- João Afonso Silva - [GitHub](https://github.com/Joaodss)
