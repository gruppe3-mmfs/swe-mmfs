[![Java CI with Maven](https://github.com/gruppe3-mmfs/swe-mmfs/actions/workflows/maven.yml/badge.svg?branch=dev)](https://github.com/gruppe3-mmfs/swe-mmfs/actions/workflows/maven.yml)
[![Coverage](.github/badges/jacoco.svg)](https://github.com/gruppe3-mmfs/swe-mmfs/dev/actions/workflows/coverage.yml)
[![Branches](.github/badges/branches.svg)](https://github.com/gruppe3-mmfs/swe-mmfs/dev/actions/workflows/coverage.yml)

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
- **Routing & Proxy:** Traefik
- **Database:** MySQL
- **Admin UI:** phpMyAdmin

---

## 📦 Cloning the Project

    git clone git@github.com:gruppe3-mmfs/swe-mmfs.git
    cd swe-mmfs

## 🛠️ Build & Run

Start the fullstack system:

    docker-compose up --build

Reset MySQL volume and restart:

    docker-compose rm -v -f mysql && docker-compose up

## 📁 Project Structure

    .
    ├── api/         # HTTP adapters (e.g., Entur, ping)
    ├── app/         # Application entrypoint and config
    ├── core/        # Domain, DTOs, ports, services
    ├── storage/     # Database adapters and persistence
    ├── test/        # Shared test scaffolding
    ├── report/      # JaCoCo aggregated report (Code coverage)
    ├── frontend/    # Vue.js frontend (Vite)
    ├── scripts/     # SQL schema and setup scripts
    ├── diagram/     # Architecture diagrams
    └── docker-compose.yml
