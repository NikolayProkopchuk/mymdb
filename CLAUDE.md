# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Purpose
This is a RESTful API service for aggregating and managing entertainment content — similar to IMDb — covering **movies** and **TV series**.

## Overview

The API serves two distinct user roles:

### 👤 Admin
- Create, update, and delete movie/series records
- Manage metadata: title, genre, release year, cast, description, poster, ratings
- Moderate user-submitted content (reviews, opinions)

### 🎬 Public User
- Browse and search movies and series
- View detailed info per title (cast, synopsis, genre, release date, etc.)
- Submit reviews / opinions and rate titles

## Build & Test Commands

```bash
# Full build
./gradlew clean build

# Run all tests
./gradlew test

# Run a single test class
./gradlew :user:application-user:test --tests "com.prokopchuk.mymdb.user.application.service.UserRegisterServiceTest"

# Checkstyle (must pass with 0 warnings in CI)
./gradlew checkstyleMain

# JaCoCo coverage report (80% minimum enforced in CI)
./gradlew jacocoTestReport

# Build Docker image (run from configuration module)
./gradlew :configuration:bootBuildImage
```

To run the app locally, start PostgreSQL first:
```bash
docker-compose up postgres
./gradlew :configuration:bootRun
```

## Architecture

This is a **hexagonal (ports-and-adapters)** architecture with a multi-module Gradle project. Each bounded context (user, media) is split into three layers:

- **domain** — Pure Java domain model (aggregates, value objects, no framework dependencies)
- **application** — Use cases (interfaces annotated `@UseCase`) and ports (input/output interfaces)
- **adapter:web** / **adapter:persistence** — Implementations of ports (Spring Web controllers, JPA repositories)

The **`configuration`** module is the Spring Boot application entry point — it declares the main class, security config, Flyway migrations, and depends on all other modules. All other modules are libraries.

### Module layout

```
common/
  domain/               # AggregateRoot, BaseEntity, value objects (UserId, FilmId)
  application/          # UseCase annotation, SelfValidation base class
  adapter/persistence/  # JPA infrastructure, UserEntity, FilmEntity, mappers
  adapter/web/          # Global exception handler, web utilities

user/
  domain/               # User aggregate root
  application/          # UserRegisterUseCase, PasswordHashingPort, LoadUserPort, RegisterUserPort
  adapter/web/          # UserRegisterController

media/
  domain/               # Film aggregate, Rating, UserRating
  application/          # CreateFilmUseCase, RateFilmUseCase, FilmDtoQuery
  adapter/web/          # CreateFilmController, FilmController, RateFilmController

configuration/          # MymdbApplication, SecurityConfig, JWT filters, Flyway migrations
```

### Key patterns

- **Custom stereotypes**: `@UseCase` (marks application services) and `@PersistenceAdapter` / `@WebAdapter` replace generic `@Service`/`@Repository`.
- **MapStruct + Spring extensions**: `mapstruct-spring-extensions` auto-registers mappers into `ConversionService`. Services call `conversionService.convert()` rather than injecting mappers directly.
- **Typed IDs**: `UserId` and `FilmId` wrap `Long` for compile-time type safety.
- **PasswordHashingPort**: Abstraction in the application layer; implemented in `configuration` via Spring Security crypto to avoid a framework dependency in the domain.

## Technology Stack

| Concern | Technology |
|---|---|
| Framework | Spring Boot 3.2.3 (configuration module: 3.5.11) |
| Java | JDK 21 |
| Database | PostgreSQL 15 (local: port 5433) |
| Migrations | Flyway |
| ORM | Spring Data JPA / Hibernate (`open-in-view: false`) |
| Object mapping | MapStruct 1.6.0.Beta1 + mapstruct-spring-extensions |
| Security | Spring Security + JJWT 0.12.6 (JWT auth) |
| Code generation | Lombok |
| Code quality | Checkstyle, JaCoCo (80% minimum coverage) |
| Integration tests | Testcontainers (PostgreSQL 12) |

## Database

Schema is managed by Flyway; migrations live in `configuration/src/main/resources/db/migration/`.

- `users` schema: `users`, `roles`, `users_roles`
- `media` schema: `films`, `users_films_rating`

Integration tests use Testcontainers via `jdbc:tc:postgresql:12:///mymdb` (configured in `application-test.yml`). The Docker socket override for Testcontainers is set in `gradle.properties`.

## CI/CD

- **Push/PR to master**: runs Checkstyle, full build + tests, JaCoCo with 80% coverage gate.
- **Release**: builds and publishes Docker image to Docker Hub as `nikolay880410/mymdb:{version}` and `latest`.

Versioning uses the Gradle release plugin (current version in `gradle.properties`).
