import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;

@QuarkusTest
public class CountryApiTest {
    @Test
    public void testGetCountryByIsoSoap(){
        given()
                .when().get("/api/v1/countries/soap/code/GR")
                .then()
                .statusCode(200)
                .body(is("Greece"));
    }

    @Test
    public void testGetAllCountriesSoap(){
        given()
                .when().get("/api/v1/countries/soap")
                .then()
                .statusCode(200)
                .body("size()" , is(25) )
                .body("[0]" , hasKey("isoCode"))
                .body("[0]" , hasKey("name"));
    }
    @Test
    public void testGetAllCountries(){
        given()
                .when().get("/api/v1/countries")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0]" , hasKey("names"))
                .body("[0]" , hasKey("currencies"));

    }
    @Test
    public void testGetCountryByName(){
        given()
                .when().get("/api/v1/countries/Isl")
                .then()
                .statusCode(200)
                .body("names()", is("Republic of Afghanistan"));

    }
    @Test
    public void testGetCountryByIsoSoap_NotFound(){
        given()
                .when().get("/api/v1/countries/soap/code/XYZ") // Ανύπαρκτος κωδικός
                .then()
                .statusCode(204);
    }


}
