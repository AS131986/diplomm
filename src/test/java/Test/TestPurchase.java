package Test;

import Data.DataHelper;
import Data.DataHeplerDB;
import Pages.Dashboard;
import Pages.Purchase;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Configuration.holdBrowserOpen;

import static com.codeborne.selenide.Selenide.*;

public class TestPurchase {

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
    void banner() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.Banner();
    }

    @Test
    void checkingTheTravelDayAndButtonsBuy() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.succesPurchase(DataHelper.CardInfoApproved);
    }

    @Test
    public void checkingMessageEmptyFieldCardNumber() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.messageEmptyFieldCardNumber();
    }

    @Test
    public void checkingMessageEmptyFieldCardMonth() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.messageEmptyFieldCardMonth();
    }

    @Test
    public void checkingMessageEmptyFieldCardYear() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.messageEmptyFieldCardYear();
    }

    @Test
    public void checkingMessageEmptyFieldOwner() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.messageEmptyFieldOwner();
    }

    @Test
    public void checkingMessageEmptyFieldCardCVV() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.messageEmptyFieldCardCVV();
    }

    @Test
    public void errorIncorrectCardNumberFormat() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.errorIncorrectCardNumberFormat();
    }

    @Test
    public void errorNonexistentMonth() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.errorNonexistentMonth();
    }

    @Test
    public void errorLastMonth() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.errorLastMonth();
    }

    @Test
    public void errorLastYear() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.errorLastYear();
    }

    @Test
    public void ownerOneWord() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.ownerOneWord();
    }

    @Test
    public void ownerInLatin() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.ownerInLatin();
    }

    @Test
    public void ownerInLatinAndCyrillic() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.ownerInLatinAndCyrillic();
    }

    @Test
    public void errorInvalidCVVFormat() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.errorInvalidCVVFormat();
    }

    @Test
    public void declinedCardPayment() {
        holdBrowserOpen = true;
        open("http://localhost:8080/");
        var dashBoardPage = new Dashboard();
        dashBoardPage.openFormPurchase();
        var purchase = new Purchase();
        purchase.declinedCardPayment();
    }
}