package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class StartPage {

    private SelenideElement head = $$("h2").find(Condition.exactText("Путешествие дня"));
    private SelenideElement debitButton = $$("button").find(Condition.exactText("Купить"));
    private SelenideElement creditButton = $$("button").find(Condition.exactText("Купить в кредит"));

    public StartPage() {
        head.shouldBe(Condition.visible);
    }

    public DebitPage pickDebitWay() {
        debitButton.click();
        return new DebitPage();
    }

    public CreditPage pickCreditWay() {
        creditButton.click();
        return new CreditPage();
    }


}
