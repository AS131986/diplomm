package Pages;

import com.github.javafaker.Faker;
import Data.DataHelper;
import com.codeborne.selenide.Condition;

import java.util.Locale;

import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Dashboard {
    public Purchase Banner() {
        $x("//*[contains(text(), 'Путешествие дня')]").should(Condition.appear);
        $x("//*[@class='Order_cardPreview__47B2k']//*[text()='Марракеш']").should(Condition.appear);
        $x("//*[@class='Order_cardPreview__47B2k']//*[text()='Сказочный Восток']").should(Condition.appear);
        $x("//*[@class='Order_cardPreview__47B2k']//*[text()='33 360 миль на карту']").should(Condition.appear);
        $x("//*[@class='Order_cardPreview__47B2k']//*[text()='До 7% на остаток по счёту']").should(Condition.appear);
        $x("//*[@class='Order_cardPreview__47B2k']//*[text()='Всего 45 000 руб.!']").should(Condition.appear);
        $x("//img[@src='/static/media/marrakech.869b2a02.jpg']").should(Condition.appear);
        $x("//*[@class='button__content']//*[text()='Купить']").click();
        return new Purchase();
    }

    public Purchase openFormPurchase() {
        $x("//*[@class='button__content']//*[text()='Купить']").click();
        return new Purchase();
    }

    public PurchaseOnCredit openFormPurchaseOnCredit() {
        $x("//*[@class='button__content']//*[text()='Купить в кредит']").click();
        return new PurchaseOnCredit();
    }
}



