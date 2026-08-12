# Country Service 🌍

This repository contains the **Country Service**, a microservice developed as the official onboarding task for my API Developer Internship at **Vodafone**.

The primary goal of this project is to demonstrate the development of a robust, modern microservice using **Java** and the **Quarkus** framework. It showcases core backend development principles, including API integration (REST & SOAP), data persistence, caching, object mapping, and comprehensive testing.

## 🛠️ Tech Stack & Tools

* **Language:** Java 21
* **Framework:** Quarkus
* **Build Tool:** Maven
* **Database & Caching:** H2 Database (File-based), Hibernate ORM with Panache, Caffeine Cache
* **Object Mapping:** MapStruct
* **API Integration:** REST Client (RestCountries API), SOAP Protocols (CXF)
* **Automated Testing:** JUnit 5, Mockito, Wiremock
* **Manual API Testing:** Insomnia

## 🚀 Getting Started

### Prerequisites
Before you begin, ensure you have the following installed on your local machine:
* **Java 21**
* **Maven**

### Configuration
To protect sensitive data and credentials, the `application.properties` file is excluded from version control (`.gitignore`).

To run this project locally, you need to create your own `application.properties` file inside the `src/main/resources/` directory.

Here is the exact example of what your `application.properties` should look like. Just make sure to fill in your actual API Token:

````
# --- Database Configuration (H2 File-based) ---
quarkus.devservices.enabled=false
quarkus.datasource.db-kind=h2
quarkus.hibernate-orm.database.generation=update
quarkus.datasource.jdbc.url=jdbc:h2:file:./country_db
quarkus.datasource.username=sa

# --- REST Client Configuration ---
quarkus.rest-client.api-key.follow-redirects=true
quarkus.rest-client.country-api.url=https://api.restcountries.com
# Put your actual external API token here:
api.external.auth-token=YOUR_API_KEY_HERE

# --- SOAP Client Configuration ---
quarkus.cxf.client.countryClient.wsdl=classpath:wsdl/CountryInfoService.wsdl
quarkus.cxf.client.countryClient.client-endpoint-url=http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso

# --- Caching Configuration (Caffeine) ---
quarkus.cache.enabled=true
quarkus.cache.caffeine.country-list.expire-after-write=1H
quarkus.cache.caffeine.rest-country-list.expire-after-write=1H

# --- Dev Environment ---
%dev.quarkus.hibernate-orm.dev-ui.allow-hql=true

# --- Test Environment Profiles ---
%test.quarkus.rest-client.country-api.url=${quarkus.wiremock.devservices.url}
%test.quarkus.hibernate-orm.database.generation=drop-and-create
%test.quarkus.datasource.jdbc.url=jdbc:h2:file:./test_country_db
%test.api.external.auth-token=test-token-12345
````

### 🏃‍♂️ How to Run Locally

With your `application.properties` in place, you can start the application in development mode (which supports live coding). Open your terminal at the root of the project and run:

````
mvn clean compile
mvn quarkus:dev  
````

## ⚙️ Core Features & Functions

### REST API & Database Management
* **Fetch & Save Countries (`fetchAndSaveCountries`)**
  Automatically retrieves country data (names, currencies) from the external REST API and persists it into the local H2 database, avoiding duplicate fetches.
* **Paginated Retrieval (`getCountries`)**
  Fetches a list of stored countries from the database with built-in pagination, allowing clients to request specific pages and sizes for optimized performance.
* **Search by Name (`findCountry`)**
  Searches the database to find a specific country using a full or partial name match (case-insensitive).
* **Filter by Currency (`findCurrencyCode`)**
  Retrieves a list of all countries that utilize a specific currency (e.g., searching for "Euro").

### SOAP Service Integration
* **Get Country by ISO (`getCountryByIso`)**
  Communicates with the external SOAP web service (`CountryInfoService`) to fetch a country's full information based on its standard ISO code.
* **Get All Countries with Caching (`getAllCountries`)**
  Retrieves a complete list of countries and their codes via SOAP. This endpoint utilizes **Caffeine Cache** (`@CacheResult`) to store the response, significantly improving performance and reducing external network calls for subsequent requests.