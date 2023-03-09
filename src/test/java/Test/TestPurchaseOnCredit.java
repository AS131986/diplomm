package Test;

import Data.DataHelper;
import Data.DataHeplerDB;
import Pages.Dashboard;
import Pages.PurchaseOnCredit;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selenide.*;

public class TestPurchaseOnCredit {

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

    @Test
    void formaPayOnCredit() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.formaPayOnCredit();
    }

    @SneakyThrows
    @Test
    void succesPurchaseOnCredit() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.succesPurchaseOnCredit(DataHelper.CardInfoApproved);
    }

    @Test
    void creditMessageEmptyFieldCardNumber() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditMessageEmptyFieldCardNumber();
    }

    @Test
    void creditMessageEmptyFieldCardMonth() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditMessageEmptyFieldCardMonth();
    }

    @Test
    void creditMessageEmptyFieldCardYear() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditMessageEmptyFieldCardYear();
    }

    @Test
    void creditMessageEmptyFieldOwner() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditMessageEmptyFieldOwner();
    }

    @Test
    void creditMessageEmptyFieldCardCVV() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditMessageEmptyFieldCardCVV();
    }

    @Test
    void creditErrorIncorrectCardNumberFormat() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditErrorIncorrectCardNumberFormat();
    }

    @Test
    void creditErrorNonexistentMonth() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditErrorNonexistentMonth();
    }

    @Test
    void creditErrorLastMonth() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditErrorLastMonth();
    }

    @Test
    void creditErrorLastYear() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditErrorLastYear();
    }

    @Test
    void creditOwnerOneWord() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditOwnerOneWord();
    }

    @Test
    void creditOwnerInLatin() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditOwnerInLatin();
    }

    @Test
    void creditOwnerInLatinAndCyrillic() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditOwnerInLatinAndCyrillic();
    }

    @Test
    void creditErrorInvalidCVVFormat() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditErrorInvalidCVVFormat();
    }

    @Test
    void creditDeclinedCardPayment() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchaseOnCredit();
        var purchaseOnCredit = new PurchaseOnCredit();
        purchaseOnCredit.creditDeclinedCardPayment();
    }
}
