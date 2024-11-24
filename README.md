This is an example distributed UI built using Mateu.

This project implements a simple booking system using micro services where features are owned e2e by each team.

## Design principles

- async over sync communication
- fat events over thin events
- eventual over transactional consistency
- avoid layer cake/anemic translation anti-patterns
- each micro has its own database. cqrs for queries instead of a shared database
- use a workflow engine for high level orchestration (e.g. sagas)
- hexagonal and ddd
- outside-in design
- code first apis
- java modules / maven modules / archunit for enforcing our layer boundaries

## Contents

This is a monorepo which contains several projects:

- an api gateway
- the booking micro service (--> mongo)
- the financial micro service (--> mariadb)
- shared stuff, mainly the dtos/events
- cqrs read database / sink (--> postgresql)
- a root shell UI micro service

and:

- docker compose to run a kafka server for development purposes
- a helm chart for deploying this app in your own cluster

## For running this project locally

Start kafka using docker compose:

```shell
docker compose up
```

Start the services, either from intellij or using maven

Open the app in http://localhost:8080

## For deploying this ap in your own cluster

### If you use cloudflare