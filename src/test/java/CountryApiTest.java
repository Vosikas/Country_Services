import db.Country;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class CountryApiTest {
    private static WireMockServer wireMockServer;
    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();
        // Ενημερώνουμε το Quarkus REST Client δυναμικά για το ποια θύρα άνοιξε ο server!
        System.setProperty("quarkus.rest-client.country-api.url", wireMockServer.baseUrl());
    }
    @AfterAll
    static void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
    @BeforeEach
    @Transactional
    void setupDataAndStubs() {
        Country.deleteAll();
        Country afghanistan = new Country();
        afghanistan.name = "Islamic Republic of Afghanistan";
        afghanistan.currency = "Afghan afghani";
        afghanistan.persist();
        Country greece = new Country();
        greece.name = "Greece";
        greece.currency = "Euro";
        greece.persist();
        wireMockServer.resetAll();
        wireMockServer.stubFor(post(urlPathEqualTo("/websamples.countryinfo/CountryInfoService.wso"))
                .withRequestBody(containing("GR"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/xml")
                        .withStatus(200)
                        .withBody("<soap:Envelope><soap:Body><CountryName>Greece</CountryName></soap:Body></soap:Envelope>")));

        wireMockServer.stubFor(post(urlPathEqualTo("/websamples.countryinfo/CountryInfoService.wso"))
                .withRequestBody(containing("XYZ"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/xml")
                        .withStatus(500)
                        .withBody("<soap:Envelope><soap:Body><soap:Fault><faultstring>Country not found</faultstring></soap:Fault></soap:Body></soap:Envelope>")));

        wireMockServer.stubFor(get(urlPathEqualTo("/countries/v5"))
                .withQueryParam("fields", equalTo("names,currencies"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{\"data\": {\"objects\": [{\"names\": {\"official\": \"Islamic Republic of Afghanistan\"}, \"currencies\": [{\"name\": \"Afghan afghani\"}]}]}}")));
    }
    @Nested
    @DisplayName("Happy Paths - Successful Operations")
    class HappyPaths {
        @Test
        @DisplayName("Should return Greece when valid ISO code GR is provided to SOAP endpoint")
        public void testGetCountryByIsoSoap() {
            given()
                    .when().get("/api/v1/countries/soap/code/GR")
                    .then()
                    .statusCode(200)
                    .body(is("Greece"));
        }
        @Test
        @DisplayName("Should return a list of countries with required fields")
        public void testGetAllCountries() {
            given()
                    .when().get("/api/v1/countries")
                    .then()
                    .statusCode(200)
                    .body("size()", greaterThan(0))
                    .body("[0]", hasKey("name"))
                    .body("[0]", hasKey("currency"));
        }
        @Test
        @DisplayName("Should return specific country by partial name match")
        public void testGetCountryByName() {
            given()
                    .when().get("/api/v1/countries/Isl")
                    .then()
                    .statusCode(200)
                    .body("name", is("Islamic Republic of Afghanistan"));
        }
    }
    @Nested
    @DisplayName("Unhappy Paths - Error Handling")
    class UnhappyPaths {
        @Test
        @DisplayName("Should return 404 Not Found when invalid ISO code is provided to SOAP endpoint")
        public void testGetCountryByIsoSoap_NotFound() {
            given()
                    .when().get("/api/v1/countries/soap/code/XYZ")
                    .then()
                    .statusCode(404);
        }
    }
}