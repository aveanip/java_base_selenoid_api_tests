package tests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

public class WdHubStatus extends TestBase {

    @Test
    @DisplayName("Проверка статуса ответа 200")
    public void statusCode200Test() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка успеха авторизации и наличия поля ready")
    public void authorizedStatusandReadyTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.qa.guru/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    @DisplayName("Проверка структуры ответа ")
    public void checkSchemeTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wh_hub_status_schema.json"));
    }

    @Test
    @DisplayName("Проверка версии текста Selenoid v3.0.14")

    public void checkVersionSelenoidTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.message", containsString("Selenoid v3.0.14"));

    }

    @Test
    @DisplayName("Проверка статуса ответа 401")
    public void unauthorizedStatusCodeTest() {
        given()
                .log().all()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    @DisplayName(" Проверка наличия фразы 'Authorization Required' при неудачной авторизацией")
    public void checkTextAuthorizationRequiredTest() {
        given()
                .log().all()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401)
                .body(containsString("Authorization Required"));
    }

    @Test
    @DisplayName(" Проверка перехода на несуществующий путь")
    public void invalidUrlTest() {
        given()
                .log().all()
                .when()
                .get("/wb/invalidurl")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    @DisplayName(" Проверка входа с невалидными данными")
    public void invalidDataTest() {
        given()
                .log().all()
                .auth().basic("user2", "12345")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }
}


