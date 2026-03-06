# Frontend – Angular 21

Egy dashboard oldal: város szélsőértékek, légitársaságonkénti útvonalak, legjobb vegyes útvonal. Magyar feliratok, egyszerű CSS. Angular service + tipizált modellek.

## Követelmények

- Node.js 20+ (Angular 21)
- npm

## Telepítés és futtatás

```bash
npm install
npm start
```

Megnyitás: http://localhost:4200  
A backendnek futnia kell a **8080**-as porton.

## Build

```bash
npm run build
```

Kimenet: `dist/airplane-frontend/`

## Projekt felépítés

- **Dashboard:** egy oldal, betölti az API adatokat (város szélsőértékek, útvonalak), signal-okkal és `@if` / `@for` control flow-val.
- **ApiService:** `http://localhost:8080/api` – airlines, flights, cities/extremes, routes/per-airline, routes/best.
- **Modellek:** `City`, `CityExtremes`, `Airline`, `Flight`, `RouteResult`, `RouteSegment` (a backend DTO-knak megfelelően).
