package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLData;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPaymentServiceTest {
    @BeforeEach
    public void openPage() {
        String url = System.getProperty("sut.url");
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        SQLData.clearTables();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    /* Positive scenario */

    @Test
    @DisplayName("Credit payment by valid card, operation is approved, DB status: APPROVED")
    void shouldConfirmCreditPayWithValidCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getValidCard());
        payment.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLData.findCreditRequestStatus());
    }

    @Test
    @DisplayName("Credit payment by declined card, operation is declined, DB status: DECLINED")
    void shouldDeniedCreditPayWithDeclinedCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getDeclinedCard());
        payment.waitNotificationFailedVisible();
        assertEquals("DECLINED", SQLData.findCreditRequestStatus());

    }

    /* Negative scenario */

    @Test
    @DisplayName("Credit payment by non existent card, operation is declined, DB status: no status")
    void shouldCreditPayNonExistentCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getNotExistedCard());
        payment.waitNotificationFailedVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by non valid card, alarm under card field: Неверный формат, DB status: no status")
    void shouldCreditPayNonValidCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getNotValidCard());
        payment.waitNotificationWrongFormatVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with expired month, alarm under month field: Истёк срок действия карты, DB status: no status")
    void shouldCreditPayInvalidDateMonthCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getExpiredMonthCard());
        payment.waitNotificationCardExpiredError();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with exceed year, alarm under field: Неверно указан срок действия карты, DB status: no status")
    void shouldPaymentExceedYearCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getExceedYearCard());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with expired year, alarm under month field: Истёк срок действия карты, DB status: no status")
    void shouldCreditPayInvalidDateYearCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getExpiredYearCard());
        payment.waitNotificationCardExpiredError();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with exceed year, alarm under field: Неверно указан срок действия карты, DB status: no status")
    void shouldCreditPayExceedYearCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getExceedYearCard());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with empty card number, alarm under card number field: Поле обязательно для заполнения, DB status: no status")
    void shouldCreditPayEmptyNumberCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getWithoutNumberCard());
        payment.waitNotificationRequiredFieldVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Debit payment by card with expiration year date, alarm under month field: Истёк срок действия карты, DB status: no status")
    void shouldCreditPayExpirationDateLessOneYearCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getExpirationDateLessOneYearCard());
        payment.waitNotificationCardExpiredError();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with null month, alarm under month field: Неверно указан срок действия карты, DB status: no status")
    void shouldCreditPayNullMonthCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getNullMonthCard());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with non existed month, alarm under month field: Неверный формат, DB status: no status")
    void shouldCreditPayNotExistedMonthCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getNotExistedMonthCard());
        payment.waitNotificationValidityErrorVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with empty fields, alarm under empty fields: Неверный формат, Поле обязательно для заполнения, DB status: no status")
    void shouldCreditPayEmptyFieldCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getEmptyFieldCard());
        payment.waitNotificationFullWrongFormatVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with Cyrillic owner, alarm under owner field: Неверный формат, DB status: no status")
    void shouldCreditPayRusNameOwnerCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getRusNameOwnerCard());
        payment.waitNotificationWrongFormatVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with non valid owner (any symbols), alarm under owner field: Неверный формат, DB status: no status")
    void shouldCreditPayNotValidNameCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getNotValidName());
        payment.waitNotificationWrongFormatVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }

    @Test
    @DisplayName("Credit payment by card with non valid CVC/CVV, alarm under CVC/CVV field: Неверный формат, DB status: no status")
    void shouldCreditPayNotValidCVCCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getNotValidCVCCard());
        payment.waitNotificationWrongFormatVisible();
        assertEquals("0", SQLData.findCountOrderEntity());
    }
}