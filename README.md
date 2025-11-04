[![Java CI with Maven](https://github.com/gruppe3-mmfs/swe-mmfs/actions/workflows/maven.yml/badge.svg?branch=dev)](https://github.com/gruppe3-mmfs/swe-mmfs/actions/workflows/maven.yml)
![Coverage](.github/badges/jacoco.svg)
![Branches](.github/badges/branches.svg)

# 🧱 SWE-MMFS: Modular Fullstack System

A fullstack project built for the ITF20319-1 25H Software Engineering
and Testing course (Gruppe 3). This system combines a modular Java backend
with a reactive Vue.js frontend, all orchestrated via Docker Compose.

---

## 🚀 Technology Overview

### 🔙 Backend

The backend is written in **Java 21**, built with a **multi-module Maven** setup, and served using the lightweight **Javalin** web framework. It follows a **hexagonal architecture** (Ports & Adapters) to ensure clean separation of concerns and maintainable code.

**Modules:**

- `core`: Contains domain logic, DTOs, service interfaces, and ports
- `api`: HTTP adapters, including routes like `/ping` and integrations with external APIs (e.g., Entur)
- `app`: Application bootstrap and configuration
- `storage`: Persistence layer with database adapters and exception handling
- `test`: Shared test scaffolding and integration test setup
- `report`: Aggregated report for code coverage generated with JaCoCo

---

### 🎨 Frontend

The frontend is built with **Vue.js** and powered by **Vite**
for fast development and optimized builds.

**Structure:**

- `components/`: Reusable UI components
- `views/`: Page-level components
- `router/`: Vue Router configuration
- `assets/`: Static files and icons

---

### ⚙️ Infrastructure

- **Containerization:** Docker & Docker Compose
- **Routing & Proxy:** Traefik [dashboard](http://dashboard.docker.localhost) (after running)
- **Database:** MySQL
- **Admin UI:** phpMyAdmin [dashboard](http://db.docker.localhost) (after running)

---

## 📦 Cloning the Project

    git clone git@github.com:gruppe3-mmfs/swe-mmfs.git
    cd swe-mmfs

## 🛠️ Build & Run

Start the fullstack system:

    docker-compose up --build

Reset MySQL volume and restart:

    docker-compose down -v && docker-compose up --build

## 📁 Project Structure

    .
    ├── api/         # HTTP adapters (e.g., Entur, ping)
    ├── app/         # Application entrypoint and config
    ├── config/      # MySQL config file
    ├── core/        # Domain, DTOs, ports, services
    ├── diagram/     # Architecture diagrams
    ├── frontend/    # Vue.js frontend (Vite)
    ├── initdb/      # MySQL schema and dummydata scripts
    ├── report/      # JaCoCo aggregated report (Code coverage)
    ├── storage/     # Database adapters and persistence
    ├── test/        # Shared test scaffolding
    └── docker-compose.yml

## Testing

I dette prosjektet bruker vi JUnit5 og Mockito for enhetstesting. Testene følger den velkjente AAA-strukturen (Arrange, Act, Assert) for å sikre både lesbarhet og forståelse av testene.

Vi har valgt å teste på følgende måter:

- Servicelagene "UserService", "TicketService" og "LocationService" testes isolert med mockede repositories.
- Exception-tester sikrer at feil i repository eller API blir håndtert på riktig måte.
- Domeneklassene "Trip", "Location", "User" osv blir ikke testet ettersom at det ikke har noe for seg i vårt prosjekt. Det kunne eventuelt vært et poeng å bruke såkalte "smoke tests" for å verifisere at konstruktørene og getterne fungerer slik de skal, men dette har vi valgt bort å gjøre.
- Våre tester demonstrerer hvordan man kan bruke ArgumentCaptor for å verifisere data som blir brukt i portene.

> Se i UserServiceUnitTests-klassen for utfyllende kommentarer vedrørende testene. Der står det forklart hvordan testene våre er bygget opp etter AAA-strukturen.