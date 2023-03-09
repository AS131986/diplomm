package Pages;

import com.github.javafaker.Faker;
import Data.DataHelper;
import com.codeborne.selenide.Condition;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import static Data.DataHeplerDB.getConn;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Purchase {

    public String generateDate(int days) {
        return LocalDate.now().minusDays(days).format(DateTimeFormatter.ofPattern("MM"));
    }

    public String generateDateYear(int Year) {
        return LocalDate.now().minusYears(Year).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    public void formaPay() {
        $x("//*[contains(text(), 'Оплата по карте')]").should(Condition.appear);
        $x("//*[@class='input__top'][text()='Номер карты']").should(Condition.appear);
        $x("//*[@class='input__top'][text()='Месяц']").should(Condition.appear);
        $x("//*[@class='input__top'][text()='Год']").should(Condition.appear);
        $x("//*[@class='input__top'][text()='Владелец']").should(Condition.appear);
        $x("//*[@class='input__top'][text()='CVC/CVV']").should(Condition.appear);
        $x(" //*[@class='button__text'][text()='Продолжить']").should(Condition.appear);

    }

    @SneakyThrows
    public void succesPurchase(DataHelper.CardInfoApproved cardInfoApproved) {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoApproved().getCardMonthApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().fullName());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='button__text'][text()='Отправляем запрос в Банк...']").should(Condition.appear, Duration.ofSeconds(10));
        $x("//*[@class='notification__title'][text()='Успешно']").should(Condition.appear, Duration.ofSeconds(30));
        $x("//*[@class='notification__content'][text()='Операция одобрена Банком.']").should(Condition.appear, Duration.ofSeconds(30));
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

    public void messageEmptyFieldCardNumber() {
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoApproved().getCardMonthApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().fullName());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='input__sub'][text()='Неверный формат']").should(Condition.appear);
    }

    public void messageEmptyFieldCardMonth() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().fullName());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='input__sub'][text()='Неверный формат']").should(Condition.appear);
    }

    public void messageEmptyFieldCardYear() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().fullName());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='input__sub'][text()='Неверный формат']").should(Condition.appear);
    }

    public void messageEmptyFieldOwner() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoApproved().getCardMonthApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='input__sub'][text()='Поле обязательно для заполнения']").should(Condition.appear);
    }

    public void messageEmptyFieldCardCVV() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoApproved().getCardMonthApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().fullName());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='input__sub'][text()='Неверный формат']").should(Condition.appear);
    }

    public void errorIncorrectCardNumberFormat() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val("1111 2222");
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoApproved().getCardMonthApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().fullName());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='input__sub'][text()='Неверный формат']").should(Condition.appear);
    }

    public void errorNonexistentMonth() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val("45");
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().fullName());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='input__sub'][text()='Неверно указан срок действия карты']").should(Condition.appear);
    }

    public void errorLastMonth() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(generateDate(30));
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(generateDateYear(0));
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().fullName());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='input__sub'][text()='Неверно указан срок действия карты']").should(Condition.appear);
    }

    public void errorLastYear() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoApproved().getCardMonthApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(generateDateYear(2));
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().fullName());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='input__sub'][text()='Истёк срок действия карты']").should(Condition.appear);
    }

    @SneakyThrows
    public void ownerOneWord() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoApproved().getCardMonthApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().name());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='notification__title'][text()='Успешно']").should(Condition.appear, Duration.ofSeconds(15));
        $x("//*[@class='notification__content'][text()='Операция одобрена Банком.']").should(Condition.appear, Duration.ofSeconds(15));
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
    public void ownerInLatin() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoApproved().getCardMonthApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val("James Oliver Dustin Dowson");
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='notification__title'][text()='Успешно']").should(Condition.appear, Duration.ofSeconds(15));
        $x("//*[@class='notification__content'][text()='Операция одобрена Банком.']").should(Condition.appear, Duration.ofSeconds(15));
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
    public void ownerInLatinAndCyrillic() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoApproved().getCardMonthApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val("James Oliver Dustin Dowson Магомед Даушев Робертович");
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoApproved().getCardCvvApproved());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='notification__title'][text()='Успешно']").should(Condition.appear, Duration.ofSeconds(15));
        $x("//*[@class='notification__content'][text()='Операция одобрена Банком.']").should(Condition.appear, Duration.ofSeconds(30));
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

    public void errorInvalidCVVFormat() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoApproved().getCardNumberApproved());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoApproved().getCardMonthApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoApproved().getCardYearApproved());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val("James Oliver Dustin Dowson Магомед Даушев Робертович");
        $x("//*[@class='input__control'][@placeholder='999']").val("1");
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='input__sub'][text()='Неверный формат']").should(Condition.appear);
    }

    @SneakyThrows
    public void declinedCardPayment() {
        $x("//input[@placeholder='0000 0000 0000 0000']").val(DataHelper.getCardInfoDeclined().getCardNumberDeclined());
        $$x("//input[@type='text' and @maxlength='2']").filter(Condition.visible).first().val(DataHelper.getCardInfoDeclined().getCardMonthDeclined());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[2]").val(DataHelper.getCardInfoDeclined().getCardYearDeclined());
        $x("//*[@class='input__control'][@placeholder='999']//preceding::input[1]").val(faker.name().fullName());
        $x("//*[@class='input__control'][@placeholder='999']").val(DataHelper.getCardInfoDeclined().getCardCvvDeclined());
        $x("//*[@class='button__text'][text()='Продолжить']").click();
        $x("//*[@class='notification__title'][text()='Ошибка']").should(Condition.appear, Duration.ofSeconds(15));
        $x("//*[@class='notification__content'][text()='Ошибка! Банк отказал в проведении операции.']").should(Condition.appear, Duration.ofSeconds(15));
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
}


