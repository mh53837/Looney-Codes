# Container za izgradnju (build) aplikacije
FROM openjdk:17-alpine AS builder

# Kopiranje izvornog koda u container
COPY ../.mvn .mvn
COPY ../mvnw .
COPY ../pom.xml .
COPY ../src src
RUN chmod +x mvnw

# Pokretanje builda
RUN ./mvnw clean package -Dmaven.test.skip

# Stvaranje containera u kojem ce se vrtiti aplikacija
FROM openjdk:17-alpine

## Ovdje je moguce instalirati alate potrebne za rad aplikacije. Vjerojatno vam nece trebati, no dobro je znati.
## Linux distro koji se koristi je Alpine, stoga se kao package manager koristi apk
#RUN apk install <nesto>

# Kopiranje izvrsnog JAR-a iz build containera u izvrsni container
COPY --from=builder target/*.jar /app.jar
# kopiraj mapu s profilnim slikama u docker
RUN mkdir -p src/main/resources
COPY --from=builder src/main/resources/profilneSlike src/main/resources/profilneSlike
# kopiraj mapu s slikama pehara u docker
COPY --from=builder src/main/resources/slikePehara src/main/resources/slikePehara

# Izlaganje porta
EXPOSE 8080

# Naredba kojom se pokrece aplikacija
ENTRYPOINT ["java","-jar","/app.jar"]
