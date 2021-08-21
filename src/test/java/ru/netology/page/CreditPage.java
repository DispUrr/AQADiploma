package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Card;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {

    private SelenideElement head = $$("h3").find(exactText("Кредит по данным карты"));
    // Поля для заполнения
    private SelenideElement cardField = $("[placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement monthField = $(byText("Месяц")).parent().$("[class=\"input__control\"]");
    private SelenideElement yearField = $(byText("Год")).parent().$("[class=\"input__control\"]");
    private SelenideElement cardholderField = $(byText("Владелец")).parent().$("[class=\"input__control\"]");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$("[class=\"input__control\"]");
    // Поп-апы - реакция на отправку формы
    private SelenideElement successOperation = $$("[class=\"notification__content\"]").find(text("Операция одобрена Банком."));
    private SelenideElement failOperation = $$("[class=\"notification__content\"]").find(text("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement wrongFormatError = $(byText("Неверный формат"));
    private ElementsCollection wrongFormatFiveError = $$(byText("Неверный формат")); // все поля не заполнены = неверный формат
    private SelenideElement validityError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredError = $(byText("Истёк срок действия карты"));
    private SelenideElement fieldRequiredError = $(byText("Поле обязательно для заполнения"));

    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public CreditPage() {
        head.shouldBe(visible);
    }
    // Заполнение полей формы
    public void fillingOfFormFields(Card card) {
        cardField.setValue(card.getCardNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        cardholderField.setValue(card.getCardholder());
        cvcField.setValue(card.getCvc());
    }
    //Ожидание появления поп-апов
    public void waitForSuccessNotification() {
        successOperation.waitUntil(visible, 10000);
    }
    public void waitForFailNotification() {
        failOperation.waitUntil(visible, 10000);
    }
    public void waitForWrongNotification() {
        wrongFormatError.waitUntil(visible, 10000);
    }
    public void waitForCardExpiredNotification() {
        cardExpiredError.waitUntil(visible, 10000);
    }
    public void waitForValidityErrorNotification() {
        validityError.waitUntil(visible, 10000);
    }
    public void waitForExpiredErrorNotification() {
        fieldRequiredError.waitUntil(visible, 10000);
    }
    public void waitForFullWrongFormatNotification() {
        wrongFormatFiveError.shouldHaveSize(5);
        fieldRequiredError.waitUntil(visible, 10000);
    }

}
