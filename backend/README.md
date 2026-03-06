# Backend – Spring Boot REST API

Java 21, Spring Boot 3.2, Maven. Rétegek: domain, data (in-memory loader), service (pathfinding, route, city, flight, airline), controller, dto, config (CORS).

## Futtatás

**Legegyszerűbb:** a `backend` mappában futtasd (dupla kattintás vagy terminálból):

```
run-backend.cmd
```

A szkript beállítja a JAVA_HOME-ot (ha van JDK a `C:\Program Files\Java\` alatt) és a Letöltésekben lévő Maven-t használja. Ha a Maven máshol van, szerkeszd a `run-backend.cmd` fájlt, és változtasd meg a `MAVEN_CMD` sort.

---

Ha a Maven a PATH-on van és a JAVA_HOME be van állítva: `mvn spring-boot:run`, vagy `mvnw.cmd spring-boot:run`.

PowerShellben, ha a Maven a Letöltésekben van (az `&` kötelező):

Teszt:

```bash
mvnw.cmd test
```

Alapértelmezett port: 8080. CORS: `http://localhost:4200` (Angular dev).

## Mintaadatok

- Városok: Budapest, Debrecen, Szeged, Pécs, Győr, Nyíregyháza, Keszthely, Sopron, Miskolc, Eger (lakosság szerint rendezve).
- Légitársaságok: Wizz Air, Ryanair, Malev.
- Járatok: köztük útvonalak a legkisebb (Keszthely) és legnagyobb (Budapest) város között, több légitársasággal és átszállással.
