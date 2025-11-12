# SWE-MMFS: Modulært fullstack-system

![Java CI with Maven](https://github.com/gruppe3-mmfs/swe-mmfs/actions/workflows/maven.yml/badge.svg)
![Coverage](.github/badges/jacoco.svg)
![Branches](.github/badges/branches.svg)

Et fullstack-prosjekt utviklet for emnet ITF20319-1 25H Software Engineering
and Testing (Gruppe 3). Systemet kombinerer en modulær Java-backend med et
reaktivt Vue.js-frontend, orkestrert via Docker Compose.

---

## Teknologioversikt

### Backend

Backend er skrevet i Java 21, bygget med en multi-modul Maven-struktur,
og kjøres med Javalin-rammeverket. Arkitekturen følger en heksagonal modell
(porter og adaptere) for å sikre tydelig ansvarsdeling og vedlikeholdbar kode.

**Moduler:**

- `core`: Inneholder domenelogikk, DTO-er, tjenestegrensesnitt og porter  
- `api`: HTTP-adaptere, endepunkt som `/ping` og integrasjon mot Entur API
- `app`: Oppstart og konfigurasjon av applikasjonen  
- `storage`: Persistenslag med databaseadaptere og feilhåndtering  
- `test`: Felles testoppsett og integrasjonstester  
- `report`: Samlet rapport for testdekning generert med JaCoCo  

---

### Frontend

Frontend er bygget med Vue.js og benytter Vite for rask utvikling og
optimaliserte bygging.

Applikasjonen nås via [localhost](http://localhost) etter oppstart.

**Struktur:**

- `components/`: Gjenbrukbare UI-komponenter  
- `views/`: Sidekomponenter  
- `router/`: Konfigurasjon for Vue Router  
- `assets/`: Statisk innhold og ikoner  

---

### Infrastruktur

- Containerisering: Docker og Docker Compose  
- Routing og proxy: Traefik ([dashboard](http://dashboard.docker.localhost))  
- Database: MySQL
- Admin-grensesnitt: phpMyAdmin ([dashboard](http://db.docker.localhost))  

---

## Instruksjoner

### Kloning av prosjektet

```bash
git clone git@github.com:gruppe3-mmfs/swe-mmfs.git
cd swe-mmfs
```

### Bygg og kjøring

Start hele systemet med følgende kommando:

```bash
docker-compose up --build
```

### Tilbakestill MySQL-volum og start på nytt

For å fjerne persisent lagrede data og starte systemet på nytt brukes følgende kommando:

```bash
docker-compose down -v && docker-compose up --build
```

### Stopp systemet / Docker

Trykk på `CTRL+C` i konsollvinduet for å stoppe systemet

## Prosjektstruktur

```bash
.
├── api/         # HTTP-adaptere (f.eks. Entur, ping)
├── app/         # Applikasjonsstart og konfigurasjon
├── config/      # MySQL-konfigurasjonsfil
├── core/        # Domene, DTO-er, porter, tjenester
├── diagram/     # Arkitekturdiagrammer
├── frontend/    # Vue.js frontend (Vite)
├── initdb/      # MySQL-skjema og dummydata
├── report/      # JaCoCo-rapport (testdekning)
├── storage/     # Databaseadaptere og persistens
├── test/        # Felles testoppsett
└── docker-compose.yml
```

## Testing

Prosjektet benytter JUnit5 og Mockito for enhetstesting. Testene følger AAA-strukturen
(Arrange, Act, Assert) for å sikre god lesbarhet og forståelse.

Vi tester på følgende måter:

- Servicelagene UserService, TicketService og LocationService testes
isolert med mockede repositories
- Egne tester for exceptions sikrer korrekt håndtering av feil i repository eller
API
- Domeneklassene Trip, Location, User osv. testes ikke, da det ikke gir verdi i
dette prosjektet. Vi vurderte såkalte "smoke tests" for å verifisere konstruktører
og gettere, men valgte å droppe dette
- Testene demonstrerer bruk av ArgumentCaptor for å verifisere hvilke data som
sendes til portene

Se UserServiceUnitTests-klassen for detaljerte kommentarer om testoppsettet og
hvordan AAA-strukturen er implementert.
