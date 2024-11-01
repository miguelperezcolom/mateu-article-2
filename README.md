This is an example distributed UI built using Mateu.

This project implements a simple booking system using micro services where features are owned e2e by each team.

## Contents

This is a monorepo which contains several projects:

- an api gateway
- the booking micro service
- the financial micro service
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