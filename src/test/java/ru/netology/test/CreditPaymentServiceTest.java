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


    @Test
    @DisplayName("Покупка в кредит, операция прошла успешно, в БД появилась запись со статусом APPROVED")
    void shouldConfirmCreditPayWithValidCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getValidCard());
        payment.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLData.findCreditRequestStatus());
    }

    @Test
    @DisplayName("Покупка в кредит, операция отклонена банком, в БД появилась запись со статусом DECLINED")
    void shouldDeniedCreditPayWithDeclinedCard() {
        val startPage = new StartPage();
        val payment = startPage.pickCreditWay();
        payment.fillingOfFormFields(DataHelper.getDeclinedCard());
        payment.waitNotificationFailedVisible();
        assertEquals("DECLINED", SQLData.findCreditRequestStatus());

    }
}