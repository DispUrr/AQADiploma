package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Card;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DebitPage {

    private SelenideElement head = $$("h3").find(exactText("Оплата по карте"));
    // Поля для заполнения
    private SelenideElement cardField = $("[placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement monthField = $(byText("Месяц")).parent().$("[class=\"input__control\"]");
    private SelenideElement yearField = $(byText("Год")).parent().$("[class=\"input__control\"]");
    private SelenideElement cardholderField = $(byText("Владелец")).parent().$("[class=\"input__control\"]");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$("[class=\"input__control\"]");
    // Поп-апы - реакция на отправку формы
    private SelenideElement successOperation = $$("[class=\"notification__content\"]").find(text("Операция одобрена Банком."));
    private SelenideElement failedOperation = $$("[class=\"notification__content\"]").find(text("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement wrongFormatError = $(byText("Неверный формат"));
    private ElementsCollection wrongFormatFiveError = $$(byText("Неверный формат")); // все поля не заполнены = неверный формат
    private SelenideElement validityError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredError = $(byText("Истёк срок действия карты"));
    private SelenideElement fieldRequiredError = $(byText("Поле обязательно для заполнения"));

    private SelenideElement cancelSuccessField = $$("[class=\"icon-button__text\"]").first();
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public DebitPage() {
        head.shouldBe(visible);
    }
    // Заполнение полей формы
    public void fillingOfFormFields(Card card) {
        cardField.setValue(card.getCardNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        cardholderField.setValue(card.getCardholder());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }
    //Ожидание появления поп-апов
    public void waitNotificationSuccessVisible() {
        successOperation.waitUntil(visible, 10000);
        cancelSuccessField.click();
    }

    public void waitNotificationFailedVisible() {
        failedOperation.waitUntil(visible, 10000);
    }

    public void waitNotificationWrongFormatVisible() {
        wrongFormatError.waitUntil(visible, 10000);
    }

    public void waitNotificationCardExpiredError() {
        cardExpiredError.waitUntil(visible, 10000);
    }

    public void waitNotificationValidityErrorVisible() {
        validityError.waitUntil(visible, 10000);
    }

    public void waitNotificationExpiredErrorVisible() {
        cardExpiredError.waitUntil(visible, 10000);
    }

    public void waitNotificationRequiredFieldVisible() {
        fieldRequiredError.waitUntil(visible, 10000);
    }

    public void waitNotificationFullWrongFormatVisible() {
        wrongFormatFiveError.shouldHaveSize(4);
        fieldRequiredError.waitUntil(visible, 10000);
    }

}
