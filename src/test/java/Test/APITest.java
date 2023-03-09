package Test;

import Data.DataHeplerDB;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import Data.DataHelperAPI;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static Data.DataHeplerDB.getConn;
import static Data.SpecificationsForApi.requestSpecification;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class APITest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    public void teardown() {
        DataHeplerDB.cleanDatabase();
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    @Test
    public void shouldRequestStartPage() {
        given()
                .baseUri("http://localhost:8080/")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType("text/html");
    }

    @SneakyThrows
    @Test
    public void postSuccessfulPayment() {
        given()
                .spec(requestSpecification())
                .body(new DataHelperAPI("111", "D", "12", "1111 2222 3333 4444", "23"))
                .post("/api/v1/pay")
                .then()
                .statusCode(HttpStatus.SC_OK);
        var runner = new QueryRunner();
        var queryAllFieldsOrderEntity = "SELECT COUNT(*) FROM order_entity WHERE credit_id is not NULL or payment_id is NULL or id is null or created is null ";
        var resultQueryAllFieldsOrderEntity = runner.execute(getConn(), queryAllFieldsOrderEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllFieldsOrderEntity.get(0));
        var queryAllCreditRequestEntity = "SELECT COUNT(*) FROM credit_request_entity WHERE id is not NULL or bank_id is not NULL or created is not NULL or status is not NULL";
        var resultQueryAllCreditRequestEntity = runner.execute(getConn(), queryAllCreditRequestEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllCreditRequestEntity.get(0));
        var queryFieldStatusPaymentEntity = "SELECT COUNT(*) FROM payment_entity WHERE status = 'APPROVED'";
        var resultQueryFieldStatusCreditRequestEntity = runner.execute(getConn(), queryFieldStatusPaymentEntity, new ScalarHandler<Long>());
        assertEquals(1, resultQueryFieldStatusCreditRequestEntity.get(0));
        var queryAllFieldsPaymentEntity = "SELECT COUNT(*) FROM payment_entity WHERE id is NULL or amount is NULL or created is NULL or status is NULL or transaction_id is NULL";
        var resultQueryAllFieldsPaymentEntity = runner.execute(getConn(), queryAllFieldsPaymentEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllFieldsPaymentEntity.get(0));
    }

    @SneakyThrows
    @Test
    public void postSuccessfulPaymentInCredit() {
        given()
                .spec(requestSpecification())
                .body(new DataHelperAPI("111", "D", "12", "1111 2222 3333 4444", "23"))
                .post("/api/v1/credit")
                .then()
                .statusCode(HttpStatus.SC_OK);
        var queryAllFieldsOrderEntity = "SELECT COUNT(*) FROM order_entity WHERE credit_id is NULL or payment_id is not NULL or id is null or created is null ";
        var queryAllCreditRequestEntity = "SELECT COUNT(*) FROM credit_request_entity WHERE id is NULL or bank_id is NULL or created is NULL or status is NULL";
        var queryFieldStatusCreditRequestEntity = "SELECT COUNT(*) FROM credit_request_entity WHERE status = 'APPROVED'";
        var queryAllFieldsPaymentEntity = "SELECT COUNT(*) FROM payment_entity WHERE id is not NULL or amount is not NULL or created is not NULL or status is not NULL or transaction_id is not NULL";
        var runner = new QueryRunner();
        var resultQueryAllFieldsPaymentEntity = runner.execute(getConn(), queryAllFieldsPaymentEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllFieldsPaymentEntity.get(0));
        var resultQueryAllCreditRequestEntity = runner.execute(getConn(), queryAllCreditRequestEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllCreditRequestEntity.get(0));
        var resultQueryFieldStatusCreditRequestEntity = runner.execute(getConn(), queryFieldStatusCreditRequestEntity, new ScalarHandler<Long>());
        assertEquals(1, resultQueryFieldStatusCreditRequestEntity.get(0));
        var resultQueryAllFieldsOrderEntity = runner.execute(getConn(), queryAllFieldsOrderEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllFieldsOrderEntity.get(0));
    }

    @SneakyThrows
    @Test
    public void postCanceledPayment() {
        given()
                .spec(requestSpecification())
                .body(new DataHelperAPI("111", faker.name().fullName(), "12", "5555 6666 7777 8888", "23"))
                .post("/api/v1/pay")
                .then()
                .statusCode(HttpStatus.SC_NOT_IMPLEMENTED);
        var runner = new QueryRunner();
        var queryAllFieldsOrderEntity = "SELECT COUNT(*) FROM order_entity WHERE credit_id is not NULL or payment_id is NULL or id is null or created is null ";
        var resultQueryAllFieldsOrderEntity = runner.execute(getConn(), queryAllFieldsOrderEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllFieldsOrderEntity.get(0));
        var queryAllCreditRequestEntity = "SELECT COUNT(*) FROM credit_request_entity WHERE id is not NULL or bank_id is not NULL or created is not NULL or status is not NULL";
        var resultQueryAllCreditRequestEntity = runner.execute(getConn(), queryAllCreditRequestEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllCreditRequestEntity.get(0));
        var queryFieldStatusPaymentEntity = "SELECT COUNT(*) FROM payment_entity WHERE status = 'DECLINED'";
        var resultQueryFieldStatusCreditRequestEntity = runner.execute(getConn(), queryFieldStatusPaymentEntity, new ScalarHandler<Long>());
        assertEquals(1, resultQueryFieldStatusCreditRequestEntity.get(0));
        var queryAllFieldsPaymentEntity = "SELECT COUNT(*) FROM payment_entity WHERE id is NULL or amount is NULL or created is NULL or status is NULL or transaction_id is NULL";
        var resultQueryAllFieldsPaymentEntity = runner.execute(getConn(), queryAllFieldsPaymentEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllFieldsPaymentEntity.get(0));
    }

    @SneakyThrows
    @Test
    public void postCanceledPaymentInCredit() {
        given()
                .spec(requestSpecification())
                .body(new DataHelperAPI("111", "D", "12", "5555 6666 7777 8888", "23"))
                .post("/api/v1/credit")
                .then()
                .statusCode(HttpStatus.SC_NOT_IMPLEMENTED);
        var runner = new QueryRunner();
        var queryAllFieldsOrderEntity = "SELECT COUNT(*) FROM order_entity WHERE credit_id is NULL or payment_id is not NULL or id is null or created is null";
        var resultQueryAllFieldsOrderEntity = runner.execute(getConn(), queryAllFieldsOrderEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllFieldsOrderEntity.get(0));
        var queryAllCreditRequestEntity = "SELECT COUNT(*) FROM credit_request_entity WHERE id is NULL or bank_id is NULL or created is NULL or status is NULL";
        var resultQueryAllCreditRequestEntity = runner.execute(getConn(), queryAllCreditRequestEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllCreditRequestEntity.get(0));
        var queryFieldStatusCreditRequestEntity = "SELECT COUNT(*) FROM credit_request_entity WHERE status = 'DECLINED'";
        var resultQueryFieldStatusCreditRequestEntity = runner.execute(getConn(), queryFieldStatusCreditRequestEntity, new ScalarHandler<Long>());
        assertEquals(1, resultQueryFieldStatusCreditRequestEntity.get(0));
        var queryAllFieldsPaymentEntity = "SELECT COUNT(*) FROM payment_entity WHERE id is not NULL or amount is not NULL or created is not NULL or status is not NULL or transaction_id is not NULL";
        var resultQueryAllFieldsPaymentEntity = runner.execute(getConn(), queryAllFieldsPaymentEntity, new ScalarHandler<Long>());
        assertEquals(0, resultQueryAllFieldsPaymentEntity.get(0));
    }
}
