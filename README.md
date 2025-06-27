![Build](https://github.com/Sherlezy/ScubMembership/actions/workflows/ci.yml/badge.svg)

# ScubMembership 🏊‍♂️📅

## Table of Contents

- [What is ScubMembership?](#what-is-scubmembership)
- [Usage](#usage)
- [Core Features](#core-features)
- [Recommendation System](#recommendation-system)
- [Code Structure](#code-structure)
- [Implemented Features](#implemented-features)
- [User Interfaces](#user-interfaces)
- [Tests](#tests)
- [Dependencies](#dependencies)
- [Our Experience](#our-experience)
- [Group Members](#group-members)
- [Next Steps](#next-steps)

---

## What is ScubMembership?

ScubMembership is a Java-based console application designed to simulate a sports membership management platform. It allows users to register, log in, view or join sports events, take quizzes, leave feedback, and more. Admins can create events, manage users, and view leaderboard stats. The app includes a recommendation engine based on favorite sports and time slot preferences.

## Usage

Make sure you have Java 17+ and Maven installed.

### Build

```bash
mvn clean package -DskipTests

```

### Run

```bash
java -jar target/ScubMembership-1.0-SNAPSHOT.jar
```

### Clean project

```bash
mvn clean
```

### Run tests

```bash
mvn test
```

### Generate Javadoc

```bash
mvn javadoc:javadoc
```

## Core Features

- 🧑 User registration/login/logout
- 🔐 Password encryption and validation
- 🏋️ Event registration & participation
- 🧠 Sport preference quizzes
- ⭐ Leaderboards and feedback
- 🛠️ Admin control panel (create sports/events, manage users,change passwords and more)

## Recommendation System

ScubMembership includes a basic recommendation system that:

- Prioritizes sports based on user preferences
- Sports events by preferred sport and most active time slots
- Incorporates user feedback through event ratings

## Code Structure

The code follows a layered, modular design organized into these packages:

- `org.ScubMembership.App` - Startup logic
- `org.ScubMembership.Login` - User authentication, sessions
- `org.ScubMembership.Users` - User models and management
- `org.ScubMembership.SportActivity` - Events, sports, time slots
- `org.ScubMembership.RecommendationLogic` - Sport suggestion logic
- `org.ScubMembership.QuizzSystem` - Quiz and leaderboard
- `org.ScubMembership.Utils` - File handling, validations

## Implemented Features

- Object-oriented design (inheritance/interfaces)
- Streams & Lambdas for filtering/sorting
- File I/O and object serialization (.ser files)
- Separation between user and admin logic
- Use of static registries (SportRegistry)
- Custom exceptions and validations
- Use of hash-based password storage

## User Interfaces

- **Command-line based UI** for both normal users and admins
- Menu-driven navigation with nested functionality
- Admin-only options (e.g. creating new sports/events, managing users)

## Tests

- JUnit 5 used for all core logic
- Mocked scanner input for registration, login, quiz modules
- Coverage includes:
  - Registration & password logic
  - Quiz system & leaderboard
  - Recommendation engine
  - Sport registry & event sorting

## Dependencies

### Main Dependencies

- **JUnit 5** - Unit testing framework

### Dev Tools

- Maven Surefire Plugin - For running tests
- JaCoCo - Code coverage reports (via `mvn test`)

## Our Experience

### Internal Organization

The team worked collaboratively using GitHub and IntelliJ IDEA. We split tasks by responsibility:

- Alessandro: Chat system, password encryption, recommendation logic, quiz & leaderboard
- Andrea: Admin system,
- Alessio: Friends manager, event system

### Git Strategy

Each member committed using their own GitHub account, as required. We worked on shared branches locally and merged when stable.

### Challenges Faced

- Alessandro: Managing password hashing and validations without compromising testability, this was caused because i decided to add
  password hashing and validation after creating the first test cases and the first password logic.
- Andrea:
- Alessio:

## Group Members

| Name                | GitHub Username    |
| ------------------- | ------------------ |
| Alessandro Ercolani | @Sherlezy          |
| Andrea Zicarelli    | @Z1CC4             |
| Alessio Stroppari   | @Alessio Stroppari |

## Next Steps

- [ ] Add real-time event notifications
- [ ] Migrate from `.ser` to JSON or database storage
- [ ] Add GUI via JavaFX or web frontend
- [ ] Add multi-language support
