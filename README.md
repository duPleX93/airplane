# Légitársaság útvonaltervező (Airline Route Planning)

Egyszerű monorepo: Spring Boot REST API + Angular dashboard. A legkisebb és legnagyobb város között keressük a legrövidebb útvonalat km-ben, légitársaságonként és vegyesen.

## Projekt felépítése

```
airplane/
├── backend/     # Java 21, Spring Boot, Maven
├── frontend/    # Angular 21
└── README.md
```

## Követelmények

- Java 21 (vagy 17), Maven 3.8+
- Node.js 18+ és npm (Angular 21)


## Futtatás

### 0. Elkészületek
```bash
run-backend.cmd-ben írjuk át a MAVEN PATH-ot
set "MAVEN_CMD=C:\Users\{user}\Downloads\apache-maven-3.9.12\bin\mvn.cmd"
```

### 1. Backend (port 8080)

```bash
cd frontend
npm run start:backend
```

### 2. Frontend (port 4200)

Előbb telepítsd a függőségeket (egyszer):

```bash
cd frontend
npm install
npm run start:frontend
```

Böngészőben: http://localhost:4200

**Fontos:** A backendnek futnia kell (8080), mert az Angular ezt hívja.

## API végpontok

| Végpont | Leírás |
|--------|--------|
| `GET /api/airlines` | Összes légitársaság |
| `GET /api/flights` | Összes járat |
| `GET /api/flights/search?from=&to=` | Járatok szűrése indulás/cél szerint (üres = nincs szűrő) |
| `GET /api/cities/extremes` | Legkisebb és legnagyobb város (lakosság) |
| `GET /api/routes/per-airline` | Légitársaságonkénti legrövidebb útvonal (legkisebb → legnagyobb város) |
| `GET /api/routes/best` | Legjobb útvonal tetszőleges légitársasággal |
